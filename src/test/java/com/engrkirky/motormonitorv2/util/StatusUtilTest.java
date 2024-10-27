package com.engrkirky.motormonitorv2.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusUtilTest {
    @Test
    void shouldGetVoltageStatus() {
        double threshold = 100;

        // Test CRITICAL high
        assertEquals(Severities.CRITICAL, StatusUtil.getVoltageStatus(1.15 * threshold, threshold));
        assertEquals(Severities.CRITICAL, StatusUtil.getVoltageStatus(120, threshold));

        // Test WARNING high
        assertEquals(Severities.WARNING, StatusUtil.getVoltageStatus(1.1 * threshold, threshold));
        assertEquals(Severities.WARNING, StatusUtil.getVoltageStatus(112, threshold));

        // Test CRITICAL low
        assertEquals(Severities.CRITICAL, StatusUtil.getVoltageStatus(0.85 * threshold, threshold));
        assertEquals(Severities.CRITICAL, StatusUtil.getVoltageStatus(80, threshold));

        // Test WARNING low
        assertEquals(Severities.WARNING, StatusUtil.getVoltageStatus(0.9 * threshold, threshold));
        assertEquals(Severities.WARNING, StatusUtil.getVoltageStatus(90, threshold));

        // Test NULL for zero voltage
        assertEquals(Severities.NULL, StatusUtil.getVoltageStatus(0, threshold));

        // Test NORMAL
        assertEquals(Severities.NORMAL, StatusUtil.getVoltageStatus(95, threshold));
        assertEquals(Severities.NORMAL, StatusUtil.getVoltageStatus(99, threshold));
    }

    @Test
    void shouldGetCurrentStatus() {
        double threshold = 100;

        // Test CRITICAL
        assertEquals(Severities.CRITICAL, StatusUtil.getCurrentStatus(1.25 * threshold, threshold));
        assertEquals(Severities.CRITICAL, StatusUtil.getCurrentStatus(130, threshold));

        // Test NULL for zero current
        assertEquals(Severities.NULL, StatusUtil.getCurrentStatus(0, threshold));

        // Test NORMAL
        assertEquals(Severities.NORMAL, StatusUtil.getCurrentStatus(95, threshold));
        assertEquals(Severities.NORMAL, StatusUtil.getCurrentStatus(99, threshold));
    }

    @Test
    void shouldGetTemperatureStatus() {
        double threshold = 100;

        // Test CRITICAL
        assertEquals(Severities.CRITICAL, StatusUtil.getTemperatureStatus(threshold, threshold));
        assertEquals(Severities.CRITICAL, StatusUtil.getTemperatureStatus(105, threshold));

        // Test NULL for zero temperature
        assertEquals(Severities.NULL, StatusUtil.getTemperatureStatus(0, threshold));

        // Test WARNING
        assertEquals(Severities.WARNING, StatusUtil.getTemperatureStatus(0.9 * threshold, threshold));
        assertEquals(Severities.WARNING, StatusUtil.getTemperatureStatus(95, threshold));

        // Test NORMAL
        assertEquals(Severities.NORMAL, StatusUtil.getTemperatureStatus(80, threshold));
        assertEquals(Severities.NORMAL, StatusUtil.getTemperatureStatus(85, threshold));
    }
}
