package com.example.demo.common;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportMaker reportMaker;
    private final ReportProjectMaker reportProjectMaker;

    @GetMapping("/users/{userUuid}")
    public ReportUserDto getReportUser(@RequestParam UUID uuid,
                                       @PathVariable UUID userUuid,
                                       @RequestParam(required = false) LocalDateTime timeFrom,
                                       @RequestParam(required = false) LocalDateTime timeTo,
                                       @RequestParam(required = false) PeriodTime periodTime) {
        return reportMaker.userInfo(uuid, userUuid, timeFrom, timeTo, periodTime);
    }

    @GetMapping("/projects/{projectUuid}")
    public ReportProjectDto getReportProject(@RequestParam UUID uuid,
                                             @PathVariable UUID projectUuid,
                                             @RequestParam(required = false) LocalDateTime timeFrom,
                                             @RequestParam(required = false) LocalDateTime timeTo,
                                             @RequestParam(required = false) PeriodTime periodTime) {
        return reportProjectMaker.projectInfo(uuid, projectUuid, timeFrom, timeTo, periodTime);
    }
}
