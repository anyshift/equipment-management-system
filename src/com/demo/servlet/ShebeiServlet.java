package com.demo.servlet;

import com.demo.service.UserService;
import com.demo.service.impl.UserServiceImpl;
import com.demo.util.Util;
import com.demo.service.ShebeiService;
import com.demo.service.impl.ShebeiServiceImpl;
import com.demo.vo.Shebei;
import com.demo.vo.ShebeiReportRecord;
import com.demo.vo.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设备模块的Servlet控制层，负责接收页面传过来的请求参数，根据action参数的值来确定页面要执行的具体操作<br>
 * 而后再调用ShebeiService业务层的方法来处理具体的业务，最后将处理完成的结果返回或跳转至相应页面
 */
//@WebServlet("/ShebeiServlet")
public class ShebeiServlet extends HttpServlet {

    /**
     * 处理Post请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //过滤编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = Util.decode(request, "action");
        if ("add".equals(action)) {//增加
            Shebei vo = new Shebei();
            //取出页面传进来的各个数据，并设置到Shebei对象的属性里
            vo.setShebeiName(Util.decode(request, "shebeiName"));
            vo.setShebeiImage(Util.decode(request, "imagePath"));
            vo.setShebeiNo(Util.decode(request, "shebeiNo"));
            vo.setShebeiType(Util.decode(request, "shebeiType"));
            vo.setShebeiCangjia(Util.decode(request, "shebeiCangjia"));
            vo.setShebeiIndate(Util.decode(request, "shebeiIndate"));
            vo.setShebeiInprice(Util.decode(request, "shebeiInprice"));
            vo.setShebeiDept(Util.decode(request, "shebeiDept"));

            String temperatureRecord = Util.decode(request, "temperature");
            String temperatureThreshold = Util.decode(request, "temperature_threshold");
            if (temperatureThreshold == null || temperatureThreshold.isEmpty()) {
                temperatureRecord = "false";
                temperatureThreshold = "0.0";
            }
            vo.setTemperatureRecord(Boolean.valueOf(temperatureRecord));
            vo.setTemperatureThreshold(Double.valueOf(temperatureThreshold));

            String humidityRecord = Util.decode(request, "humidity");
            String  humidityThreshold = Util.decode(request, "humidity_threshold");
            if (humidityThreshold == null || humidityThreshold.isEmpty()) {
                humidityRecord = "false";
                humidityThreshold = "0.0";
            }
            vo.setHumidityRecord(Boolean.valueOf(humidityRecord));
            vo.setHumidityThreshold(Double.valueOf(humidityThreshold));

            String noiseRecord = Util.decode(request, "noise");
            String noiseThreshold = Util.decode(request, "noise_threshold");
            if (noiseThreshold == null || noiseThreshold.isEmpty()) {
                noiseRecord = "false";
                noiseThreshold = "0.0";
            }
            vo.setNoiseRecord(Boolean.valueOf(noiseRecord));
            vo.setNoiseThreshold(Double.valueOf(noiseThreshold));

            String voltageRecord = Util.decode(request, "voltage");
            String voltageThreshold = Util.decode(request, "voltage_threshold");
            if (voltageThreshold == null || voltageThreshold.isEmpty()) {
                voltageRecord = "false";
                voltageThreshold = "0.0";
            }
            vo.setVoltageRecord(Boolean.valueOf(voltageRecord));
            vo.setVoltageThreshold(Double.valueOf(voltageThreshold));

            ShebeiService shebeiService = new ShebeiServiceImpl();
            //调用Service层增加方法（add），增加记录
            shebeiService.add(vo);
            this.redirectList(request, response);
        } else if ("delete".equals(action)) {//删除
            //取出表要删除的设备记录的主键
            long id = Long.parseLong(Util.decode(request, "id"));
            ShebeiService shebeiService = new ShebeiServiceImpl();
            //调用Service层删除方法（delete），将对应的记录删除
            shebeiService.delete(id);
            this.redirectList(request, response);
        } else if ("edit".equals(action)) {//修改
            //取出页面传进来的各个数据，并设置到Shebei对象的属性里
            Shebei vo = new Shebei();
            ShebeiService shebeiService = new ShebeiServiceImpl();
            Long deviceId = Long.valueOf(Util.decode(request, "id"));
            vo.setId(deviceId);

            String imagePath = Util.decode(request, "imagePath");
            if (imagePath == null || imagePath.isEmpty()) {
                vo.setShebeiImage(shebeiService.get(deviceId).getShebeiImage());
            } else {
                vo.setShebeiImage(Util.decode(request, "imagePath"));
            }

            vo.setShebeiName(Util.decode(request, "shebeiName"));
            vo.setShebeiNo(Util.decode(request, "shebeiNo"));
            vo.setShebeiType(Util.decode(request, "shebeiType"));
            vo.setShebeiCangjia(Util.decode(request, "shebeiCangjia"));
            vo.setShebeiIndate(Util.decode(request, "shebeiIndate"));
            vo.setShebeiInprice(Util.decode(request, "shebeiInprice"));
            vo.setShebeiDept(Util.decode(request, "shebeiDept"));
            vo.setShebeiStatus(Util.decode(request, "shebeiStatus"));

            String temperatureRecord = Util.decode(request, "temperature");
            String temperatureThreshold = Util.decode(request, "temperature_threshold");
            if (temperatureThreshold == null || temperatureThreshold.isEmpty()) {
                temperatureRecord = "false";
                temperatureThreshold = "0";
            }
            vo.setTemperatureRecord(Boolean.valueOf(temperatureRecord));
            vo.setTemperatureThreshold(Double.valueOf(temperatureThreshold));

            String humidityRecord = Util.decode(request, "humidity");
            String humidityThreshold = Util.decode(request, "humidity_threshold");
            if (humidityThreshold == null || humidityThreshold.isEmpty()) {
                humidityRecord = "false";
                humidityThreshold = "0";
            }
            vo.setHumidityRecord(Boolean.valueOf(humidityRecord));
            vo.setHumidityThreshold(Double.valueOf(humidityThreshold));

            String noiseRecord = Util.decode(request, "noise");
            String noiseThreshold = Util.decode(request, "noise_threshold");
            if (noiseThreshold == null || noiseThreshold.isEmpty()) {
                noiseRecord = "false";
                noiseThreshold = "0";
            }
            vo.setNoiseRecord(Boolean.valueOf(noiseRecord));
            vo.setNoiseThreshold(Double.valueOf(noiseThreshold));

            String voltageRecord = Util.decode(request, "voltage");
            String voltageThreshold = Util.decode(request, "voltage_threshold");
            if (voltageThreshold == null || voltageThreshold.isEmpty()) {
                voltageRecord = "false";
                voltageThreshold = "0";
            }
            vo.setVoltageRecord(Boolean.valueOf(voltageRecord));
            vo.setVoltageThreshold(Double.valueOf(voltageThreshold));

            //调用Service层更新方法（update），更新记录
            shebeiService.update(vo);
            this.redirectList(request, response);
        } else if ("faultReport".equalsIgnoreCase(action)) {
            ShebeiService shebeiService = new ShebeiServiceImpl();
            if (shebeiService.faultReport(Long.valueOf(Util.decode(request, "id")), Util.decode(request, "faultReason"))) {
                shebeiService.updateReportStatus(Long.valueOf(Util.decode(request, "id")));
            }
            this.redirectList(request, response);
        } else if ("reportRecord".equalsIgnoreCase(action)) {
            ShebeiService shebeiService = new ShebeiServiceImpl();
            List<ShebeiReportRecord> reportRecords = shebeiService.getReportRecords();
            request.getSession().setAttribute("list", reportRecords);
            response.sendRedirect("shebei_report_record.jsp");
        } else if ("updateShebeiFaultStatus".equalsIgnoreCase(action)) {
            ShebeiService shebeiService = new ShebeiServiceImpl();
            Long deviceId = Long.valueOf(Util.decode(request, "id"));
            String deviceStatus = Util.decode(request, "status");
            if ("正常".equalsIgnoreCase(deviceStatus) || "损坏".equalsIgnoreCase(deviceStatus)) {
                if (shebeiService.deleteReportRecord(deviceId)) {
                    shebeiService.updateShebeiStatus(deviceId, deviceStatus, Boolean.FALSE);
                }
            } else {
                if (shebeiService.updateReportFaultStatus(deviceId, Boolean.TRUE)) {
                    shebeiService.updateShebeiStatus(deviceId, deviceStatus, Boolean.FALSE);
                }
            }
            List<ShebeiReportRecord> reportRecords = shebeiService.getReportRecords();
            request.getSession().setAttribute("list", reportRecords);
            response.sendRedirect("shebei_report_record.jsp");
        } else if ("dispatchRepairer".equalsIgnoreCase(action)) {
            ShebeiService shebeiService = new ShebeiServiceImpl();
            Long uid = Long.valueOf(Util.decode(request, "uid"));
            Long deviceId = Long.valueOf(Util.decode(request, "deviceId"));
            if (shebeiService.addDispatchRepairer(deviceId, uid, "待维修")) {
                if (shebeiService.updateShebeiStatus(deviceId, "待维修", Boolean.FALSE)) {
                    shebeiService.addRepairRecord(uid, deviceId, "待维修", "");
                }
            }
            this.redirectList(request, response);
        } else if ("get".equalsIgnoreCase(action) || "editPre".equalsIgnoreCase(action)) {//根据主键ID，查询详情信息并跳转到详情页面或编辑页面
            Serializable id = Util.decode(request, "id");//取出页面传入的主键，用于查询详情
            ShebeiService shebeiService = new ShebeiServiceImpl();
            Shebei vo = shebeiService.get(id);
            request.getSession().setAttribute("vo", vo);
            String to = "get".equalsIgnoreCase(action) ? "info" : "edit";//判断是去详情显示页面还是编辑页面
            response.sendRedirect("shebei_" + to + ".jsp");
        } else {//默认去列表页面
            this.redirectList(request, response);
        }
    }

    /**
     * 处理Get请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);//Get请求和Post请求的处理是一样的，所以把request、response转交给Post方法就好
    }

    /**
     * 根据参数，查询出条例条件的记录集合，最后将数据返回给调用处或者将数据集合设置到session域里，再跳转到对应的列表页面
     *
     * @param request
     * @param response
     */
    private void redirectList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //查询列和关键字
        String searchColumn = Util.decode(request, "searchColumn");
        String keyword = Util.decode(request, "keyword");
        Map<String, Object> params = new HashMap<>();//用来保存控制层传进来的参数(查询条件)

        params.put("keyword", keyword);//查询的关键字

        if (params.get("keyword") != null && !"".equals(params.get("keyword"))) {
            String keywordStr = (String)params.get("keyword");
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(keywordStr);
            if (isNum.matches()) {
                params.put("searchColumn", "shebei_no");//要查询的列
            } else {
                params.put("searchColumn", searchColumn);//要查询的列
            }
        }

        ShebeiService shebeiService = new ShebeiServiceImpl();
        Map<String, Object> map = shebeiService.list(params);
        request.getSession().setAttribute("list", map.get("list"));

        Integer totalRecord = (Integer) map.get("totalCount");//根据查询条件取出对应的总记录数，用于分页
        String pageNum = Util.decode(request, "pageNum");//封装分页参数
        com.demo.util.PageBean<Object> pb = new com.demo.util.PageBean(Integer.valueOf(pageNum != null ? pageNum : "1"), totalRecord);
        params.put("startIndex", pb.getStartIndex());
        params.put("pageSize", pb.getPageSize());
        List list = (List) shebeiService.list(params).get("list");//根据分页参数startIndex、pageSize查询出来的最终结果list
        pb.setServlet("ShebeiServlet");
        pb.setSearchColumn(searchColumn);
        pb.setKeyword(keyword);
        pb.setList(list);

        UserService userService = new UserServiceImpl();
        List<User> workers = userService.getWorkers();

        request.getSession().setAttribute("pageBean", pb);
        request.getSession().setAttribute("list", pb.getList());
        request.getSession().setAttribute("workers", workers);

        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if ("普通员工".equalsIgnoreCase(loginUser.getUserType())) {
            Integer recordCount = shebeiService.countDispatchStaffRecord(loginUser.getId());
            if (recordCount != null) {
                request.getSession().setAttribute("unhandledWorks", recordCount);
                request.getSession().setAttribute("targetUid", loginUser.getId());
            }
        }

        response.sendRedirect("shebei_list.jsp");
    }
}
