package com.engrkirky.motormonitorv2.dto;

import java.time.LocalDateTime;

public record MetricsDTO(
        Long id,
        LocalDateTime timestamp,
        String motorID,
        double line1Voltage,
        double line2Voltage,
        double line3Voltage,
        double line1Current,
        double line2Current,
        double line3Current,
        double temperature
) {}
