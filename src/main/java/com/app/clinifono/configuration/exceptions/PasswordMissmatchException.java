package com.app.clinifono.configuration.exceptions;

public class PasswordMissmatchException extends RuntimeException{
    public PasswordMissmatchException(String message) {
        super(message);
    }
}
