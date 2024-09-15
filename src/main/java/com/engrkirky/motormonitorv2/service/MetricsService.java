package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.dto.*;

import java.util.List;

public interface MetricsService {
    LatestMetricsDTO getLatestMetrics(String id, double ratedVoltage, double ratedCurrent, double maxTemperature);
    List<VoltageDTO> getVoltageTrend(String id, int limit);
    List<CurrentDTO> getCurrentTrend(String id, int limit);
    List<TemperatureDTO> getTemperatureTrend(String id, int limit);
    MetricsSummaryDTO getMetricsSummary(String id, double ratedVoltage, double ratedCurrent, double maxTemperature);
    void addMetrics(MetricsDTO metricsDTO);
    List<MetricsDTO> getMetricsLogs(String id, int limit);
}
