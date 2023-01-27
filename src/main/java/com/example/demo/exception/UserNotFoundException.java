package com.example.demo.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID uuid) {
        super(String.format("User with this uuid: %s is not exists", uuid));
    }

    public UserNotFoundException(String login) {
        super(String.format("User with this login: %s is not exists", login));
    }
}
