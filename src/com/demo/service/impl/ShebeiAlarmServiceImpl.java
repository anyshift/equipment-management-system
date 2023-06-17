package com.demo.service.impl;

import com.demo.dao.ShebeiAlarmDAO;
import com.demo.dao.impl.ShebeiAlarmDAOImpl;
import com.demo.service.ShebeiAlarmService;
import com.demo.vo.ShebeiAlarm;

import java.util.List;

public class ShebeiAlarmServiceImpl implements ShebeiAlarmService {
    @Override
    public boolean addAlarm(Long deviceId, String alarmType, String alarmTime) {
        ShebeiAlarmDAO deviceAlarmDAO = new ShebeiAlarmDAOImpl();
        return deviceAlarmDAO.addAlarm(deviceId, alarmType, alarmTime);
    }

    @Override
    public List<ShebeiAlarm> getAlarms(Long deviceId, String filterDate) {
        ShebeiAlarmDAO deviceAlarmDAO = new ShebeiAlarmDAOImpl();
        return deviceAlarmDAO.getAlarms(deviceId, filterDate);
    }
}
