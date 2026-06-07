package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.dto.*;

import java.util.List;

/**
 * Service for managing motor metrics.
 */
public interface MetricsService {

    /**
     * Retrieves the latest metrics for a motor.
     *
     * @param id motor identifier
     * @param ratedVoltage rated voltage
     * @param ratedCurrent rated current
     * @param maxTemperature maximum temperature
     * @return latest metrics
     */
    LatestMetricsDTO getLatestMetrics(
            String id,
            double ratedVoltage,
            double ratedCurrent,
            double maxTemperature);

    /**
     * Retrieves voltage trend data.
     *
     * @param id motor identifier
     * @param limit time range in minutes
     * @return voltage trend data
     */
    List<VoltageDTO> getVoltageTrend(String id, int limit);

    /**
     * Retrieves current trend data.
     *
     * @param id motor identifier
     * @param limit time range in minutes
     * @return current trend data
     */
    List<CurrentDTO> getCurrentTrend(String id, int limit);

    /**
     * Retrieves temperature trend data.
     *
     * @param id motor identifier
     * @param limit time range in minutes
     * @return temperature trend data
     */
    List<TemperatureDTO> getTemperatureTrend(String id, int limit);

    /**
     * Retrieves a summary of motor metrics.
     *
     * @param id motor identifier
     * @param ratedVoltage rated voltage
     * @param ratedCurrent rated current
     * @param maxTemperature maximum temperature
     * @return metrics summary
     */
    MetricsSummaryDTO getMetricsSummary(
            String id,
            double ratedVoltage,
            double ratedCurrent,
            double maxTemperature);

    /**
     * Publishes metrics and generated alarms.
     *
     * @param motorID motor identifier
     * @param metricsDTO metrics data
     * @param ratedVoltage rated voltage
     * @param ratedCurrent rated current
     * @param maxTemperature maximum temperature
     * @return motor identifier
     */
    String publishMetrics(
            String motorID,
            MetricsDTO metricsDTO,
            double ratedVoltage,
            double ratedCurrent,
            double maxTemperature);

    /**
     * Stores a metrics record.
     *
     * @param motorID motor identifier
     * @param metricsDTO metrics data
     * @return motor identifier
     */
    String addMetrics(String motorID, MetricsDTO metricsDTO);

    /**
     * Retrieves metrics logs.
     *
     * @param id motor identifier
     * @param limit time range in minutes
     * @return metrics logs
     */
    List<MetricsDTO> getMetricsLogs(String id, int limit);

    /**
     * Retrieves alarms based on the latest metrics.
     *
     * @param id motor identifier
     * @param ratedVoltage rated voltage
     * @param ratedCurrent rated current
     * @param maxTemperature maximum temperature
     * @return list of alarms
     */
    List<AlarmDTO> getAlarms(
            String id,
            double ratedVoltage,
            double ratedCurrent,
            double maxTemperature);
}
