package com.example.demo.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class TimesheetForManagerDto {

    private BigDecimal costWorkUserInProject;
    private Long timeWorkUserInProject;
}
