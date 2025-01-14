/*
* Copyright 2017 Nokia Solutions and Networks
* Licensed under the Apache License, Version 2.0,
* see license.txt file for details.
*/
package org.rf.ide.core.rflint;


public enum RfLintViolationSeverity {
    ERROR,
    WARNING,
    IGNORE;

    public static RfLintViolationSeverity from(final String severity) {
        if ("E".equals(severity)) {
            return ERROR;
        } else if ("W".equals(severity)) {
            return WARNING;
        } else if ("I".equals(severity)) {
            return IGNORE;
        }
        throw new IllegalArgumentException();
    }

    public String severitySwitch() {
        switch (this) {
            case ERROR:
                return "e";
            case WARNING:
                return "w";
            case IGNORE:
                return "i";
        }
        throw new IllegalStateException();
    }

}
