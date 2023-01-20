package com.example.demo.exception;

import java.util.UUID;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(final UUID projectUuid) {
        super(String.format("Project with this uuid: %s is not exists", projectUuid));
    }
}
