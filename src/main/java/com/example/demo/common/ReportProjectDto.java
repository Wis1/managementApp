package com.example.demo.common;

import com.example.demo.timesheet.dto.TimesheetForManagerDto;
import com.example.demo.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class ReportProjectDto {

    private UUID projectUuid;
    private Map<UserDto, TimesheetForManagerDto> usersInfoInProject;
    private BigDecimal costProject;
    private Long timeWorkInProject;
    private Boolean inBudget;
}
