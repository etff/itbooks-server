package com.example.itbooks.user.application;

public class EmailDuplicationException extends RuntimeException {
    public EmailDuplicationException(String email) {
        super("email is already existed: " + email);
    }
}
