package com.engrkirky.motormonitorv2.dto;

import com.engrkirky.motormonitorv2.util.Severities;

import java.time.LocalDateTime;

public record AlarmDTO(
        LocalDateTime timestamp,
        String motorID,
        String alarm,
        Severities severity
) {
}
