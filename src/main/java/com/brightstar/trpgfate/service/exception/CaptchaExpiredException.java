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
}
