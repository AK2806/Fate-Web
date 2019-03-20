package com.brightstar.trpgfate.service.exception;

public class EmailVerifyExpiredException extends Exception {
    public EmailVerifyExpiredException() {
        super();
    }

    public EmailVerifyExpiredException(String message) {
        super(message);
    }

    public EmailVerifyExpiredException(String message, Throwable err) {
        super(message, err);
    }

    public EmailVerifyExpiredException(Throwable err) {
        super(err);
    }

    public EmailVerifyExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}