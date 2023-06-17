package com.demo.dao;

import com.demo.vo.ShebeiAlarm;

import java.util.List;

public interface ShebeiAlarmDAO {
    boolean addAlarm(Long deviceId, String alarmType, String alarmTime);

    List<ShebeiAlarm> getAlarms(Long deviceId, String filterDate);
}
