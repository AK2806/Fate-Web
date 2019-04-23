package com.brightstar.trpgfate.service.exception;

public class InvalidGameStateException extends Exception {
    public InvalidGameStateException() {
    }

    public InvalidGameStateException(String message) {
        super(message);
    }

    public InvalidGameStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidGameStateException(Throwable cause) {
        super(cause);
    }

    public InvalidGameStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
