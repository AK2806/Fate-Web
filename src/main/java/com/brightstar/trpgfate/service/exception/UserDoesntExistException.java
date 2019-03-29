package com.brightstar.trpgfate.service.exception;

public class UserDoesntExistException extends Exception {
    public UserDoesntExistException() {
        super();
    }

    public UserDoesntExistException(String message) {
        super(message);
    }

    public UserDoesntExistException(String message, Throwable err) {
        super(message, err);
    }

    public UserDoesntExistException(Throwable err) {
        super(err);
    }

    public UserDoesntExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
