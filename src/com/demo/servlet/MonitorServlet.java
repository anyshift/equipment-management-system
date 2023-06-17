package com.demo.servlet;

import com.demo.service.ShebeiAlarmService;
import com.demo.service.ShebeiService;
import com.demo.service.impl.ShebeiAlarmServiceImpl;
import com.demo.service.impl.ShebeiServiceImpl;
import com.demo.util.Util;
import com.demo.vo.Shebei;
import com.demo.vo.ShebeiAlarm;
import com.demo.vo.ShebeiDispatch;
import com.demo.vo.ShebeiMonitor;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MonitorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = Util.decode(req, "action");
        if ("deviceAlarm".equalsIgnoreCase(action)) {
            String deviceId = Util.decode(req, "deviceId");
            String filterDate = Util.decode(req, "filter");
            ShebeiAlarmService deviceAlarmService = new ShebeiAlarmServiceImpl();
            List<ShebeiAlarm> deviceAlarms = deviceAlarmService.getAlarms(Long.valueOf(deviceId), filterDate);

            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(deviceAlarms);
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(result);
        } else if ("dispatchRecords".equalsIgnoreCase(action)) {
            String uid = Util.decode(req, "uid");
            String userType = Util.decode(req, "userType");
            ShebeiService deviceService = new ShebeiServiceImpl();
            List<ShebeiDispatch> dispatchStaffRecords = deviceService.getDispatchStaffRecords(Long.valueOf(uid), userType);

            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dispatchStaffRecords);
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(result);
        } else {
            ShebeiService shebeiService = new ShebeiServiceImpl();
            ShebeiAlarmService deviceAlarmService = new ShebeiAlarmServiceImpl();
            List<Shebei> list = (List) shebeiService.list(new HashMap()).get("list");
            List<ShebeiMonitor> deviceMonitors = new ArrayList<>();

            list.forEach(device -> {
                ShebeiMonitor deviceMonitor = new ShebeiMonitor();
                deviceMonitor.setDevice(device);

                Random random = new Random();
                DecimalFormat decimalFormat = new DecimalFormat("#.0");
                deviceMonitor.setCurrentTemperature(Double.valueOf(decimalFormat.format(deviceMonitor.getCurrentTemperature() + random.nextDouble() * 32.0)));
                deviceMonitor.setCurrentHumidity(Double.valueOf(decimalFormat.format(deviceMonitor.getCurrentHumidity() + random.nextDouble() * 30.0)));
                deviceMonitor.setCurrentNoise(Double.valueOf(decimalFormat.format(deviceMonitor.getCurrentNoise() + random.nextDouble() * 62.0)));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                if (device.getTemperatureRecord() && deviceMonitor.getCurrentTemperature() > device.getTemperatureThreshold()) {
                    deviceAlarmService.addAlarm(device.getId(), "运行温度过高", LocalDateTime.now().format(formatter));
                }
                if (device.getHumidityRecord() && deviceMonitor.getCurrentHumidity() > device.getHumidityThreshold()) {
                    deviceAlarmService.addAlarm(device.getId(), "外部湿度过高", LocalDateTime.now().format(formatter));
                }
                if (device.getNoiseRecord() && deviceMonitor.getCurrentNoise() > device.getNoiseThreshold()) {
                    deviceAlarmService.addAlarm(device.getId(), "外部噪音过高", LocalDateTime.now().format(formatter));
                }

                deviceMonitors.add(deviceMonitor);
            });

            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(deviceMonitors);
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(result);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = Util.decode(req, "action");
        if ("dispatch".equalsIgnoreCase(action)) {
            String uid = Util.decode(req, "uid");
            String deviceId = Util.decode(req, "deviceId");
            String reason = Util.decode(req, "reason");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String now = LocalDateTime.now().format(formatter);

            ShebeiService deviceService = new ShebeiServiceImpl();
            if (deviceService.dispatchStaff(Long.valueOf(uid), Long.valueOf(deviceId), reason, now)) {
                deviceService.updateDeviceDispatchStatus(Long.valueOf(deviceId), Boolean.TRUE);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(Boolean.TRUE);
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(result);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = Util.decode(req, "action");
        String deviceId = Util.decode(req, "deviceId");
        if ("markDeviceOK".equalsIgnoreCase(action)) {
            ShebeiService deviceService = new ShebeiServiceImpl();
            if (deviceService.updateDispatchFinished(Long.valueOf(deviceId), Boolean.TRUE, "无需修理")) {
                deviceService.updateDeviceDispatchStatus(Long.valueOf(deviceId), Boolean.FALSE);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(Boolean.TRUE);
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(result);
        } else if ("markDeviceBad".equalsIgnoreCase(action)) {
            String reason = Util.decode(req, "reason");
            ShebeiService shebeiService = new ShebeiServiceImpl();
            if (shebeiService.faultReport(Long.valueOf(deviceId), reason)) {
                shebeiService.updateReportStatus(Long.valueOf(deviceId));
                shebeiService.updateDispatchFinished(Long.valueOf(deviceId), Boolean.TRUE, "已上报故障");
                shebeiService.updateDeviceDispatchStatus(Long.valueOf(deviceId), Boolean.FALSE);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reason);
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(result);
        }
    }
}
