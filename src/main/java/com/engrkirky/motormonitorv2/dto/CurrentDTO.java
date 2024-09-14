package com.engrkirky.motormonitorv2.dto;

import java.time.LocalDateTime;

public record CurrentDTO(
        LocalDateTime timestamp,
        double line1Current,
        double line2Current,
        double line3Current
) {}
