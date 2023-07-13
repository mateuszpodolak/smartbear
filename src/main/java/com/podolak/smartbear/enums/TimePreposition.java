package com.podolak.smartbear.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimePreposition {
    O_CLOCK("o'clock"),
    PAST("past"),
    TO("to");

    private final String value;

    public static TimePreposition evaluateTimePreposition(int minutes) {
        if (minutes == 0) {
            return O_CLOCK;
        } else if (minutes > 30) {
            return TO;
        } else {
            return PAST;
        }
    }
}
