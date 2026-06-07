package com.engrkirky.motormonitorv2.handler;

import java.util.Date;

/**
 * Error response payload.
 *
 * @param statusCode HTTP status code
 * @param timestamp error timestamp
 * @param message error message
 * @param description error details
 */
public record ErrorMessage(
        int statusCode,
        Date timestamp,
        String message,
        String description
) {}
