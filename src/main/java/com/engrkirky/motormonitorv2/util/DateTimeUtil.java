package com.engrkirky.motormonitorv2.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Utility methods for date and time operations.
 */
public class DateTimeUtil {
    private DateTimeUtil(){}

    /**
     * Returns a time range ending at the current time.
     *
     * @param minutes number of minutes in the range
     * @return start and end timestamps
     */
    public static LocalDateTime[] getTimeRange(int minutes) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minus(minutes, ChronoUnit.MINUTES);

        return new LocalDateTime[]{startTime, endTime};
    }
}
