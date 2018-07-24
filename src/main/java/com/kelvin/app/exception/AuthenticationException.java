package com.kelvin.app.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String msg) {
        super(msg);
    }
}
