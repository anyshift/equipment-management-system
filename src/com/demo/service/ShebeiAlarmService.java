package com.demo.service;

import com.demo.vo.ShebeiAlarm;

import java.util.List;

public interface ShebeiAlarmService {
    boolean addAlarm(Long deviceId, String alarmType, String alarmTime);
    List<ShebeiAlarm> getAlarms(Long deviceId, String filterDate);
}
