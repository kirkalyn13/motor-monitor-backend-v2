package com.engrkirky.motormonitorv2.util;

/**
 * Utility class for determining metric status levels.
 */
public class StatusUtil {

    private StatusUtil(){}

    /**
     * Returns the status for a voltage reading.
     *
     * @param value measured voltage
     * @param threshold voltage threshold
     * @return status severity
     */
    public static Severities getVoltageStatus(double value, double threshold) {
        if (value == 0) return Severities.NULL;
        if (value >= (1.15 * threshold)) return Severities.CRITICAL;
        if (value >= (1.1 * threshold)) return Severities.WARNING;
        if (value <= (0.85 * threshold)) return Severities.CRITICAL;
        if (value <= (0.9 * threshold)) return Severities.WARNING;

        return Severities.NORMAL;
    }

    /**
     * Returns the status for a current reading.
     *
     * @param value measured current
     * @param threshold current threshold
     * @return status severity
     */
    public static Severities getCurrentStatus(double value, double threshold) {
        if (value >= (1.25 * threshold)) return Severities.CRITICAL;
        if (value == 0) return Severities.NULL;

        return Severities.NORMAL;
    }

    /**
     * Returns the status for a temperature reading.
     *
     * @param value measured temperature
     * @param threshold temperature threshold
     * @return status severity
     */
    public static Severities getTemperatureStatus(double value, double threshold) {
        if (value >= threshold) return Severities.CRITICAL;
        if (value == 0) return Severities.NULL;
        if (value >= (0.9 * threshold)) return Severities.WARNING;

        return Severities.NORMAL;
    }
}

