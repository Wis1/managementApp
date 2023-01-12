package com.example.demo.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID uuid) {
        super("User with this uuid: " + uuid + " is not exists");
    }
    public UserNotFoundException(String login) {
        super("User with this login: " + login + " is not exists");
    }
}
