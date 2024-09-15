package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.LatestMetricsDTO;
import com.engrkirky.motormonitorv2.dto.MetricStatusDTO;
import com.engrkirky.motormonitorv2.model.Metrics;
import com.engrkirky.motormonitorv2.util.StatusUtil;

public class LatestMetricsMapperImpl implements LatestMetrcisMapper {
    @Override
    public LatestMetricsDTO convertToLatestMetricsDTO(Metrics metrics, double ratedVoltage, double ratedCurrent, double maxTemperature) {
        return new LatestMetricsDTO(
                metrics.getTimestamp(),
                metrics.getMotorID(),
                new MetricStatusDTO(metrics.getLine1Voltage(), StatusUtil.getVoltageStatus(metrics.getLine1Voltage(), ratedVoltage)),
                new MetricStatusDTO(metrics.getLine1Voltage(), StatusUtil.getVoltageStatus(metrics.getLine2Voltage(), ratedVoltage)),
                new MetricStatusDTO(metrics.getLine1Voltage(), StatusUtil.getVoltageStatus(metrics.getLine3Voltage(), ratedVoltage)),
                new MetricStatusDTO(metrics.getLine1Current(), StatusUtil.getVoltageStatus(metrics.getLine1Current(), ratedCurrent)),
                new MetricStatusDTO(metrics.getLine2Current(), StatusUtil.getVoltageStatus(metrics.getLine2Current(), ratedCurrent)),
                new MetricStatusDTO(metrics.getLine3Current(), StatusUtil.getVoltageStatus(metrics.getLine3Current(), ratedCurrent)),
                new MetricStatusDTO(metrics.getTemperature(), StatusUtil.getVoltageStatus(metrics.getTemperature(), ratedCurrent))
        );
    }
}
