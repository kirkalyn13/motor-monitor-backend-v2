package com.engrkirky.motormonitorv2.mapper;

import com.engrkirky.motormonitorv2.dto.MetricsDTO;
import com.engrkirky.motormonitorv2.model.Metrics;
import org.springframework.stereotype.Component;

@Component
public class MetricsMapperImpl implements MetricsMapper {
    @Override
    public MetricsDTO convertToDTO(Metrics metrics) {
        return new MetricsDTO(
                metrics.getId(),
                metrics.getTimestamp(),
                metrics.getMotorID(),
                metrics.getLine1Voltage(),
                metrics.getLine2Voltage(),
                metrics.getLine3Voltage(),
                metrics.getLine1Current(),
                metrics.getLine2Current(),
                metrics.getLine3Current(),
                metrics.getTemperature()
        );
    }

    @Override
    public Metrics convertToEntity(MetricsDTO metricsDTO) {
        return Metrics.builder()
                .id(metricsDTO.id())
                .timestamp(metricsDTO.timestamp())
                .motorID(metricsDTO.motorID())
                .line1Voltage(metricsDTO.line1Voltage())
                .line2Voltage(metricsDTO.line2Voltage())
                .line3Voltage(metricsDTO.line3Voltage())
                .line1Current(metricsDTO.line1Current())
                .line2Current(metricsDTO.line2Current())
                .line3Current(metricsDTO.line3Current())
                .temperature(metricsDTO.temperature())
                .build();
    }
}
