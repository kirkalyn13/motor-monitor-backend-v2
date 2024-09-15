package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.LatestMetricsDTO;
import com.engrkirky.motormonitorv2.dto.MetricStatusDTO;
import com.engrkirky.motormonitorv2.model.Metrics;

public class LatestMetricsMapperImpl implements LatestMetrcisMapper {
    @Override
    public LatestMetricsDTO convertToLatestMetricsDTO(Metrics metrics) {
        return new LatestMetricsDTO(
                metrics.getTimestamp(),
                metrics.getMotorID(),
                new MetricStatusDTO(metrics.getLine1Voltage(), "NORMAL"),
                new MetricStatusDTO(metrics.getLine2Voltage(), "NORMAL"),
                new MetricStatusDTO(metrics.getLine3Voltage(), "NORMAL"),
                new MetricStatusDTO(metrics.getLine1Current(), "NORMAL"),
                new MetricStatusDTO(metrics.getLine2Current(), "NORMAL"),
                new MetricStatusDTO(metrics.getLine3Current(), "NORMAL"),
                new MetricStatusDTO(metrics.getTemperature(), "NORMAL")
        );
    }
}
