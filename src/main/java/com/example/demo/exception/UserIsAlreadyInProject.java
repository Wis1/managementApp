package com.example.demo.exception;

import java.util.UUID;

public class UserIsAlreadyInProject extends RuntimeException {
    public UserIsAlreadyInProject(final UUID userUuid, final UUID projectUuid) {

        super(String.format("User with this uuid: %s is already in project with uuid: %s", userUuid, projectUuid));
    }
}
