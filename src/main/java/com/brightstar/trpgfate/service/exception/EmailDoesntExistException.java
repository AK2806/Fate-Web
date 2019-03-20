package com.brightstar.trpgfate.service.exception;

public class EmailDoesntExistException extends Exception {
    public EmailDoesntExistException() {
        super();
    }

    public EmailDoesntExistException(String message) {
        super(message);
    }

    public EmailDoesntExistException(String message, Throwable err) {
        super(message, err);
    }

    public EmailDoesntExistException(Throwable err) {
        super(err);
    }

    public EmailDoesntExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
