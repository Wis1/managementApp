package com.example.demo.exception;

import java.util.UUID;

public class TimesheetIsNotFoundException extends RuntimeException {
    public TimesheetIsNotFoundException(final UUID uuid) {
        super(String.format("Timesheet with id: %s is not found", uuid));
    }
}
