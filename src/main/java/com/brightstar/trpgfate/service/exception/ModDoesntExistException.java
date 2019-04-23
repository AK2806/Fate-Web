package com.brightstar.trpgfate.service.exception;

public class ModDoesntExistException extends Exception {
    public ModDoesntExistException() {
    }

    public ModDoesntExistException(String message) {
        super(message);
    }

    public ModDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModDoesntExistException(Throwable cause) {
        super(cause);
    }

    public ModDoesntExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
