package com.example.itbooks.auth.application;

public class LoginNotFoundException extends RuntimeException {
    public LoginNotFoundException() {
    }

    public LoginNotFoundException(String message) {
        super(message);
    }

    public LoginNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginNotFoundException(Throwable cause) {
        super(cause);
    }
}
