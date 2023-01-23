package com.example.demo.timesheet.controller;

import com.example.demo.timesheet.dto.TimesheetDto;
import com.example.demo.timesheet.dto.TimesheetForm;
import com.example.demo.timesheet.service.TimesheetService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timesheet")
public class TimesheetController {

    private final TimesheetService timesheetService;

    @PostMapping
    public void addNewTimesheet(@NotNull @RequestParam UUID uuid, @RequestBody TimesheetForm timesheetForm) {
        timesheetService.addNewTimesheet(uuid, timesheetForm);
    }

    @GetMapping
    public List<TimesheetDto> getTimesheet(@RequestParam UUID userUuid) {

        return timesheetService.getTimesheetForUser(userUuid);
    }

}
