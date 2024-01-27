package com.example.bank.exception.exceptions;

public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist(String email) {
        super("User " + email + " already exist");
    }
}
