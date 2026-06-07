package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.dto.AlarmDTO;
import com.engrkirky.motormonitorv2.dto.MetricsDTO;

import java.util.List;

/**
 * Service for managing and analyzing alarms.
 */
public interface AlarmService {

    /**
     * Retrieves alarm history for a motor.
     *
     * @param id motor identifier
     * @param limit time range in minutes
     * @return alarm history
     */
    List<AlarmDTO> getAlarmsHistoryByMotorID(String id, int limit);

    /**
     * Stores alarm records.
     *
     * @param id motor identifier
     * @param alarmDTOs alarms to store
     * @return motor identifier
     */
    String addAlarms(String id, List<AlarmDTO> alarmDTOs);

    /**
     * Analyzes metrics and generates alarms.
     *
     * @param metricsDTO metrics data
     * @param ratedVoltage rated voltage
     * @param ratedCurrent rated current
     * @param maxTemperature maximum temperature
     * @return generated alarms
     */
    List<AlarmDTO> analyzeMetrics(
            MetricsDTO metricsDTO,
            double ratedVoltage,
            double ratedCurrent,
            double maxTemperature);
}
