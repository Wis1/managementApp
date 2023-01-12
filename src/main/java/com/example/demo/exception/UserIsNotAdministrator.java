package com.example.demo.exception;

import java.util.UUID;

public class UserIsNotAdministrator extends RuntimeException {
    public UserIsNotAdministrator(UUID uuid) {

        super("User with this uuid: " + uuid + " is not administrator");
    }
}
