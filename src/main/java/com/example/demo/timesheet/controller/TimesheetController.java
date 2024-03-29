package com.example.demo.timesheet.controller;

import com.example.demo.timesheet.dto.TimesheetDto;
import com.example.demo.timesheet.dto.TimesheetForm;
import com.example.demo.timesheet.dto.TimesheetSearch;
import com.example.demo.timesheet.service.TimesheetService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/users/{uuid}")
    public void addNewTimesheet(@NotNull @PathVariable UUID uuid, @RequestBody TimesheetForm timesheetForm) {
        timesheetService.addNewTimesheet(uuid, timesheetForm);
    }

    @GetMapping("/users/{uuid}")
    public List<TimesheetDto> getTimesheet(@PathVariable UUID uuid) {
        return timesheetService.getTimesheetForUser(uuid);
    }

    @PatchMapping
    public void updateTimesheet(@RequestParam UUID uuid, @RequestBody TimesheetForm timesheetForm) {
        timesheetService.updateTimesheet(uuid, timesheetForm);
    }

    @DeleteMapping
    public void deleteTimesheet(@RequestParam UUID uuid) {
        timesheetService.deleteTimesheet(uuid);
    }

    @GetMapping("/filter")
    public Page<TimesheetDto> filterByCriteria(@RequestParam UUID uuid, @RequestBody TimesheetSearch timesheetSearch) {
        return timesheetService.filterByCriteria(uuid, timesheetSearch);
    }
}
