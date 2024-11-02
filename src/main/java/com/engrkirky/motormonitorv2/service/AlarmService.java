package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.dto.MetricsDTO;

import java.util.List;

public interface AlarmService {
    List<AlarmDTO> getAlarmsHistoryByMotorID(String id, int limit);
    String addAlarms(String id, List<AlarmDTO> alarmDTOs);
    List<AlarmDTO> analyzeMetrics(MetricsDTO metricsDTO, double ratedVoltage, double ratedCurrent, double maxTemperature);
}
