package com.engrkirky.motormonitorv2;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    private DateTimeUtil(){}

    public static LocalDateTime[] getTimeRange(int minutes) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minus(minutes, ChronoUnit.MINUTES);

        return new LocalDateTime[]{startTime, endTime};
    }
}
