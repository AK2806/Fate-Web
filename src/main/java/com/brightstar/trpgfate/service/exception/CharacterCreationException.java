package com.brightstar.trpgfate.service.exception;

public class CharacterCreationException extends Exception {
    public CharacterCreationException() {
    }

    public CharacterCreationException(String message) {
        super(message);
    }

    public CharacterCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CharacterCreationException(Throwable cause) {
        super(cause);
    }

    public CharacterCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
