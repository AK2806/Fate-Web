package com.brightstar.trpgfate.service.exception;

public class EmailVerifySendingException extends Exception {
    public EmailVerifySendingException() {
        super();
    }

    public EmailVerifySendingException(String message) {
        super(message);
    }

    public EmailVerifySendingException(String message, Throwable err) {
        super(message, err);
    }
}
