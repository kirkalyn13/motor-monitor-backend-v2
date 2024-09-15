package com.engrkirky.motormonitorv2.dto;

import java.time.LocalDateTime;

public record LatestMetricsDTO(
        LocalDateTime timestamp,
        String motorID,
        MetricStatusDTO line1Voltage,
        MetricStatusDTO line2Voltage,
        MetricStatusDTO line3Voltage,
        MetricStatusDTO line1Current,
        MetricStatusDTO line2Current,
        MetricStatusDTO line3Current,
        MetricStatusDTO temperature
) {
}
