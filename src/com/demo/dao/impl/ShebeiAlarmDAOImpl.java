package com.demo.dao.impl;

import com.demo.dao.ShebeiAlarmDAO;
import com.demo.dao.ShebeiDAO;
import com.demo.util.Util;
import com.demo.vo.ShebeiAlarm;
import com.demo.vo.ShebeiReportRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShebeiAlarmDAOImpl implements ShebeiAlarmDAO {
    @Override
    public boolean addAlarm(Long deviceId, String alarmType, String alarmTime) {
        String sql = "INSERT INTO `t_shebei_alarm` (`device_id`, `alarm_type`, `alarm_time`) VALUES(?,?,?)";
        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, deviceId);
            ps.setString(2, alarmType);
            ps.setString(3, alarmTime);
            ps.execute();
            ps.close();
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<ShebeiAlarm> getAlarms(Long deviceId, String filterDate) {
        List<ShebeiAlarm> deviceAlarms = new ArrayList<>();
        try {
            Connection c = Util.getConnection();
            Statement s = c.createStatement();
            String sql;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String today = LocalDate.now().format(formatter);
            String yesterday = LocalDate.now().minusDays(1).format(formatter);
            String twoDaysAgo = LocalDate.now().minusDays(2).format(formatter);

            if ("today".equalsIgnoreCase(filterDate)) {
                sql = "select * from `t_shebei_alarm` where `device_id` = " + deviceId + " and `alarm_time` > '" + today + "' order by `alarm_time` desc limit 30";
            } else if ("yesterday".equalsIgnoreCase(filterDate)) {
                sql = "select * from `t_shebei_alarm` where `device_id` = " + deviceId + " and `alarm_time` > '" + yesterday + "' and `alarm_time` < '" + today + "' order by `alarm_time` desc limit 30";
            } else {
                sql = "select * from `t_shebei_alarm` where `device_id` = " + deviceId + " and `alarm_time` > '" + twoDaysAgo + "' and `alarm_time` < '" + yesterday + "' order by `alarm_time` desc limit 30";
            }

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                ShebeiAlarm deviceAlarm = new ShebeiAlarm();
                deviceAlarm.setDeviceId(deviceId);
                deviceAlarm.setAlarmType(rs.getString("alarm_type"));
                deviceAlarm.setAlarmTime(rs.getString("alarm_time"));
                deviceAlarms.add(deviceAlarm);
            }
            rs.close();
            c.close();
            return deviceAlarms;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
