package com.example.bank.exception.exceptions;

public class ValidationRequestException extends RuntimeException {
    public ValidationRequestException(String email) {
        super("Validation failed for user: " + email);
    }
}
