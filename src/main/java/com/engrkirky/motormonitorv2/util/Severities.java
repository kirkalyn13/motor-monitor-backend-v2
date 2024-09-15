package com.engrkirky.motormonitorv2.util;

public enum Severities {
    NULL("null"),
    NORMAL("normal"),
    WARNING("warning"),
    CRITICAL("critical");

    public final String severity;

    private Severities(String severity) {
        this.severity = severity;
    }

}
