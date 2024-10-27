package com.engrkirky.motormonitorv2.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateTimeUtilTest {
    @Test
    void shouldGetTimeRange() {
        int minutes = 30;

        LocalDateTime[] timeRange = DateTimeUtil.getTimeRange(minutes);
        LocalDateTime startTime = timeRange[0];
        LocalDateTime endTime = timeRange[1];

        LocalDateTime now = LocalDateTime.now();
        assertTrue(ChronoUnit.SECONDS.between(endTime, now) < 1,
                "endTime should be close to the current time");

        assertEquals(minutes, ChronoUnit.MINUTES.between(startTime, endTime),
                "startTime should be " + minutes + " minutes before endTime");
    }
}
