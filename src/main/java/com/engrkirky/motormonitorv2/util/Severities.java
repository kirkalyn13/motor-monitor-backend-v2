package com.engrkirky.motormonitorv2.util;

/**
 * Alarm severity levels.
 */
public enum Severities {

    /**
     * Missing or unavailable data.
     */
    NULL("null"),

    /**
     * Normal operating condition.
     */
    NORMAL("normal"),

    /**
     * Warning condition.
     */
    WARNING("warning"),

    /**
     * Critical condition.
     */
    CRITICAL("critical");

    /**
     * Severity value.
     */
    public final String severity;

    private Severities(String severity) {
        this.severity = severity;
    }
}
