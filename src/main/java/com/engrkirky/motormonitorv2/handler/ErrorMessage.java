package com.engrkirky.motormonitorv2.handler;

import java.util.Date;

public record ErrorMessage(
        int statusCode,
        Date timestamp,
        String message,
        String description
) {}
