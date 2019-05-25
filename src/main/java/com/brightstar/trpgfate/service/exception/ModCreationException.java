package com.brightstar.trpgfate.service.exception;

public class ModCreationException extends Exception {
    public ModCreationException() {
    }

    public ModCreationException(String message) {
        super(message);
    }

    public ModCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModCreationException(Throwable cause) {
        super(cause);
    }

    public ModCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
