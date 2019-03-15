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
}
