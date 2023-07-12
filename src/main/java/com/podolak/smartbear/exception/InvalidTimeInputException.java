package com.podolak.smartbear.exception;

public class InvalidTimeInputException extends BaseException {
    public InvalidTimeInputException() {
        super("Provided invalid time as a parameter.");
    }
}
