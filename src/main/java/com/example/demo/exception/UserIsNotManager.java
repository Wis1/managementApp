package com.example.demo.exception;

import java.util.UUID;

public class UserIsNotManager extends RuntimeException {
    public UserIsNotManager(final UUID uuid) {
        super(String.format("User with this uuid: %s is not manager", uuid));
    }
}
