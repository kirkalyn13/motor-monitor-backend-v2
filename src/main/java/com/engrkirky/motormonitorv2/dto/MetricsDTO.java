package com.engrkirky.motormonitorv2.dto;

import java.util.Date;

public record MetricsDTO(
        Long id,
        Date timestamp,
        Integer motorID,
        Double line1Voltage,
        Double line2Voltage,
        Double line3Voltage,
        Double line1Current,
        Double line2Current,
        Double line3Current,
        Double temperature
) {
}
