package com.example.demo.timesheet.mapper;

import com.example.demo.timesheet.domain.Timesheet;
import com.example.demo.timesheet.dto.TimesheetDto;

import java.util.List;

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

    public static List<TimesheetDto> mapToListTimesheetDto(final List<Timesheet> timesheets) {

        return timesheets.stream()
                .map(TimesheetMapper::mapToTimesheetDto)
                .toList();
    }
}
