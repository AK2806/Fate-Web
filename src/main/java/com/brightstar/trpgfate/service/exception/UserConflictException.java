package com.brightstar.trpgfate.service.exception;

public class UserConflictException extends Exception {
    public UserConflictException() {
        super();
    }

    public UserConflictException(String message) {
        super(message);
    }

    public UserConflictException(String message, Throwable err) {
        super(message, err);
    }

    public UserConflictException(Throwable err) {
        super(err);
    }

    public UserConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
