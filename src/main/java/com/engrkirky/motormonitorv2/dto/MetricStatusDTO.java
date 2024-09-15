package com.engrkirky.motormonitorv2.dto;

import com.engrkirky.motormonitorv2.util.Severities;

public record MetricStatusDTO(
        double value,
        Severities status
) {}
