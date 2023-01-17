package com.example.demo.exception;

public class UserIsAlreadyExists extends RuntimeException {
    public UserIsAlreadyExists(String login) {

        super("User with this login: " + login + " is already exists");
    }
}
