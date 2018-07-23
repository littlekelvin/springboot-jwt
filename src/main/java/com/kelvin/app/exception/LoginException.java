package com.kelvin.app.exception;

public class LoginException extends RuntimeException {
    public LoginException() {
        super();
    }

    public LoginException(String msg) {
        super(msg);
    }
}
