package com.example.bank.exception.exceptions;

public class UserNotExist extends RuntimeException {

    public UserNotExist(String email) {
        super("User with " + email + " not exist");
    }
}
