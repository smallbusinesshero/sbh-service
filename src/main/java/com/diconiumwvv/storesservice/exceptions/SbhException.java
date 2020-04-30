package com.diconiumwvv.storesservice.exceptions;

public class SbhException extends Exception {
    public SbhException(String message) {
        super(message);
    }

    public SbhException(String message, Throwable cause) {
        super(message, cause);
    }

    public SbhException(Throwable cause) {
        super(cause);
    }

    public SbhException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
