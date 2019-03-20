package com.brightstar.trpgfate.service.exception;

public class EmailExistsException extends Exception {
    public EmailExistsException() {
        super();
    }

    public EmailExistsException(String message) {
        super(message);
    }

    public EmailExistsException(String message, Throwable err) {
        super(message, err);
    }

    public EmailExistsException(Throwable err) {
        super(err);
    }

    public EmailExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
