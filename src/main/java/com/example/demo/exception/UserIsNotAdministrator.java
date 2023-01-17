package com.example.demo.exception;

import java.util.UUID;

public class UserIsNotAdministrator extends RuntimeException {
    public UserIsNotAdministrator(UUID uuid) {

        super(String.format("User with this uuid: %s is not administrator", uuid));
    }
}
