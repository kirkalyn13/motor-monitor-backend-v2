package com.engrkirky.motormonitorv2.dto;

import java.time.LocalDateTime;
import java.util.Date;

public record MetricsDTO(
        Long id,
        LocalDateTime timestamp,
        int motorID,
        double line1Voltage,
        double line2Voltage,
        double line3Voltage,
        double line1Current,
        double line2Current,
        double line3Current,
        double temperature
) {}
