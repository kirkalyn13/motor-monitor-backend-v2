package com.engrkirky.motormonitorv2.dto;

import java.time.LocalDateTime;

public record VoltageDTO(
        LocalDateTime timestamp,
        double line1Voltage,
        double line2Voltage,
        double line3Voltage
) {}
