package com.example.demo.common;

import com.example.demo.timesheet.dto.TimesheetForManagerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public class ReportUserDto {

    private UUID userUuid;
    private BigDecimal costWorkUser;
    private Map<UUID, TimesheetForManagerDto> dataWorkUserInProject;
    private Long timeUserInAllProjects;
}
