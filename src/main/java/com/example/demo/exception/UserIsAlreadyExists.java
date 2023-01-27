package com.example.demo.exception;

public class UserIsAlreadyExists extends RuntimeException {
    public UserIsAlreadyExists(String login) {
        super(String.format("User with this login: %s is already exists", login));
    }
}
