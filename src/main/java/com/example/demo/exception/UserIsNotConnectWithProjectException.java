package com.example.demo.exception;

import java.util.UUID;

public class UserIsNotConnectWithProjectException extends RuntimeException {
    public UserIsNotConnectWithProjectException(final UUID userUuid, final UUID projectUuid) {
        super(String.format("User with uuid: %s is not connect with project uuid: %s.", userUuid, projectUuid));
    }
}
