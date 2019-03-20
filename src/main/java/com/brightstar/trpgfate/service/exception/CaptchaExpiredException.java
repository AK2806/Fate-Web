package com.brightstar.trpgfate.service.exception;

public class CaptchaExpiredException extends Exception {
    public CaptchaExpiredException() {
        super();
    }

    public CaptchaExpiredException(String message) {
        super(message);
    }

    public CaptchaExpiredException(String message, Throwable err) {
        super(message, err);
    }

    public CaptchaExpiredException(Throwable err) {
        super(err);
    }

    public CaptchaExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
