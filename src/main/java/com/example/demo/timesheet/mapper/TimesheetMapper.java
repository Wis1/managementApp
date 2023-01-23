package com.example.demo.timesheet.mapper;

import com.example.demo.timesheet.domain.Timesheet;
import com.example.demo.timesheet.dto.TimesheetDto;

import java.util.List;
import java.util.stream.Collectors;

public class TimesheetMapper {


    public static TimesheetDto mapToTimesheetDto(final Timesheet timesheet) {

        return TimesheetDto.builder()
                .timesheetUuid(timesheet.getUuid())
                .startUserInProject(timesheet.getStartUserInProject())
                .endUserInProject(timesheet.getEndUserInProject())
                .projectUuid(timesheet.getProjectUsers().getProject().getUuid())
                .nameProject(timesheet.getProjectUsers().getProject().getName())
                .startProject(timesheet.getProjectUsers().getProject().getStartProject())
                .endProject(timesheet.getProjectUsers().getProject().getEndProject())
                .userUuid(timesheet.getProjectUsers().getUser().getUuid())
                .lastname(timesheet.getProjectUsers().getUser().getLastname())
                .firstname(timesheet.getProjectUsers().getUser().getFirstname())
                .build();
    }

    public static List<TimesheetDto> mapToListTimesheetDto(final List<Timesheet> timesheetList) {

        return timesheetList.stream()
                .map(TimesheetMapper::mapToTimesheetDto)
                .collect(Collectors.toList());

    }
}
