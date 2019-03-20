package com.brightstar.trpgfate.service.exception;

public class ResetterExpiredException extends Exception {
    public ResetterExpiredException() {
    }

    public ResetterExpiredException(String message) {
        super(message);
    }

    public ResetterExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResetterExpiredException(Throwable cause) {
        super(cause);
    }

    public ResetterExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
