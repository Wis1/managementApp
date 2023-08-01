package com.example.demo.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TimesheetSearch {

    private UUID userUuid;
    private String userFirstname;
    private String userLastname;
    private LocalDateTime timeFrom;
}
