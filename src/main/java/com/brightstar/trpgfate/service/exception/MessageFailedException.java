package com.brightstar.trpgfate.service.exception;

public class MessageFailedException extends Exception {
    public MessageFailedException() {
    }

    public MessageFailedException(String message) {
        super(message);
    }

    public MessageFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageFailedException(Throwable cause) {
        super(cause);
    }

    public MessageFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
