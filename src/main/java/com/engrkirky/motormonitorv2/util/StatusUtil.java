package com.engrkirky.motormonitorv2.util;

public class StatusUtil {

    private StatusUtil(){}

    public static Severities getVoltageStatus(double value, double threshold) {
        if (value >= (1.15 * threshold)) {
            return Severities.CRITICAL;
        } else if (value >= (1.1 * threshold)) {
            return Severities.WARNING;
        } else if (value <= (0.85 * threshold)) {
            return Severities.CRITICAL;
        } else if (value <= (0.9 * threshold)) {
            return Severities.WARNING;
        } else if (value == 0) {
            return Severities.NULL;
        } else {
            return Severities.NORMAL;
        }
    }

    public static Severities getCurrentStatus(double value, double threshold) {
        if (value >= (1.25 * threshold)) {
            return Severities.CRITICAL;
        } else if (value == 0) {
            return Severities.NULL;
        } else {
            return Severities.NORMAL;
        }
    }

    public static Severities getTemperatureStatus(double value, double threshold) {
        if (value >= threshold) {
            return Severities.CRITICAL;
        } else if (value == 0) {
            return Severities.NULL;
        } else if (value >= (0.9 * threshold)) {
            return Severities.WARNING;
        } else {
            return Severities.NORMAL;
        }
    }
}

