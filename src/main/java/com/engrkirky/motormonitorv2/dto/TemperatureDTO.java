package com.engrkirky.motormonitorv2.dto;

import java.time.LocalDateTime;

public record TemperatureDTO(
        LocalDateTime timestamp,
        double temperature
) {}
