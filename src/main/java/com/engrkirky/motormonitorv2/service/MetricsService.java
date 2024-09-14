package com.engrkirky.motormonitorv2.service;

import com.engrkirky.motormonitorv2.dto.CurrentDTO;
import com.engrkirky.motormonitorv2.dto.MetricsDTO;
import com.engrkirky.motormonitorv2.dto.TemperatureDTO;
import com.engrkirky.motormonitorv2.dto.VoltageDTO;

import java.util.List;

public interface MetricsService {
    List<MetricsDTO> getLatestMetrics(int id, double ratedVoltage, double ratedCurrent, double maxTemperature);
    List<VoltageDTO> getVoltageTrend(int id, int limit);
    List<CurrentDTO> getCurrentTrend(int id, int limit);
    List<TemperatureDTO> getTemperatureTrend(int id, int limit);
    List<Integer> getMetricsSummary(int id, double ratedVoltage, double ratedCurrent, double maxTemperature);
    Long addMetrics(MetricsDTO metricsDTO);
    List<MetricsDTO> getMetricsLogs(int id, int limit);
}
