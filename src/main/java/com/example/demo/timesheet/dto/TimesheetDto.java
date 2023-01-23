package com.example.demo.timesheet.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TimesheetDto {

    private final UUID timesheetUuid;

    private final LocalDateTime startUserInProject;

    private final LocalDateTime endUserInProject;

    private final UUID projectUuid;

    private final String nameProject;

    private final LocalDateTime startProject;

    private final LocalDateTime endProject;

    private final UUID userUuid;

    private final String lastname;

    private final String firstname;


}
