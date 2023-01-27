package com.example.demo.common;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.project.service.ProjectService;
import com.example.demo.timesheet.dto.TimesheetDto;
import com.example.demo.timesheet.dto.TimesheetForManagerDto;
import com.example.demo.timesheet.service.TimesheetService;
import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class ReportMaker {
    private final TimesheetService timesheetService;
    private final UserRepository userRepository;
    private final ProjectService projectService;

    public ReportUserDto userInfo(UUID uuid, UUID userUuid, LocalDateTime timeFrom, LocalDateTime timeTo, PeriodTime periodTime) {

        projectService.checkIsManagerOrAdministrator(uuid);

        User user = userRepository.findByUuid(userUuid).
                orElseThrow(() -> new UserNotFoundException(userUuid));

        List<TimesheetDto> timesheetDtoListInPeriod = new ArrayList<>();

        if (periodTime != null) {
            switch (periodTime) {
                case YEAR ->
                        timesheetDtoListInPeriod = filterTimesheetForUserAndPeriod(userUuid, PeriodTime.YEAR.returnDate(), LocalDateTime.now());
                case MONTH ->
                        timesheetDtoListInPeriod = filterTimesheetForUserAndPeriod(userUuid, PeriodTime.MONTH.returnDate(), LocalDateTime.now());
                case WEEK ->
                        timesheetDtoListInPeriod = filterTimesheetForUserAndPeriod(userUuid, PeriodTime.WEEK.returnDate(), LocalDateTime.now());
            }
        } else {
            timesheetDtoListInPeriod = filterTimesheetForUserAndPeriod(userUuid, timeFrom, timeTo);
        }

        Set<UUID> projectUuidList = createProjectUuidList(timesheetDtoListInPeriod);

        return ReportUserDto.builder()
                .userUuid(userUuid)
                .costWorkUser(calculateCostUserInProjects(calculateTimeUserInAllProjects(timesheetDtoListInPeriod), user.getSalaryPerHour()))
                .dataWorkUserInProject(createMapWithProjectUuidAndCostUserInProject(user, projectUuidList, timesheetDtoListInPeriod))
                .timeUserInAllProjects(calculateTimeUserInAllProjects(timesheetDtoListInPeriod))
                .build();
    }

    public Long calculateTimeUserInAllProjects(List<TimesheetDto> timesheetDtoList) {
        return timesheetDtoList.stream()
                .mapToLong(t ->
                        Duration.between(t.getStartUserInProject(), t.getEndUserInProject()).toHours())
                .sum();
    }

    public BigDecimal calculateCostUserInProjects(Long timeUserInProjects, BigDecimal costPerHour) {
        return costPerHour.multiply(BigDecimal.valueOf(timeUserInProjects));
    }

    public List<TimesheetDto> filterTimesheetForUserAndPeriod(UUID userUuid, final LocalDateTime timeFrom, final LocalDateTime timeTo) {
        List<TimesheetDto> timesheetList = timesheetService.getTimesheetForUser(userUuid);
        List<TimesheetDto> timesheetDtoListInPeriod;
        if (timeFrom == null || timeTo == null) {
            timesheetDtoListInPeriod = timesheetList;
        } else {

            timesheetDtoListInPeriod = timesheetList.stream()
                    .filter(t -> t.getStartUserInProject().isAfter(timeFrom))
                    .filter(t -> t.getEndUserInProject().isBefore(timeTo))
                    .toList();
        }
        return timesheetDtoListInPeriod;
    }

    public Map<UUID, TimesheetForManagerDto> createMapWithProjectUuidAndCostUserInProject(User user, Set<UUID> projectUuidSet, List<TimesheetDto> timesheetDtoListInPeriod) {

        Map<UUID, TimesheetForManagerDto> costWorkUserInProjectMap = new HashMap<>();
        BigDecimal costWorkUserInProject;
        long timeWorkUserInProject;

        for (UUID projectUuid : projectUuidSet) {
            Set<TimesheetDto> timesheetDtoSet = timesheetDtoListInPeriod.stream().filter(timesheetDto ->
                    timesheetDto.getProjectUuid().equals(projectUuid)).collect(toSet());
            timeWorkUserInProject = timesheetDtoSet.stream()
                    .mapToLong(t -> Duration.between(t.getStartUserInProject(), t.getEndUserInProject()).toHours())
                    .sum();
            costWorkUserInProject = timesheetDtoSet.stream()
                    .map(t -> user.getSalaryPerHour().multiply(BigDecimal.valueOf(
                            Duration.between(t.getStartUserInProject(), t.getEndUserInProject()).toHours())))
                    .reduce(BigDecimal::add)
                    .orElseThrow();
            TimesheetForManagerDto timesheetForManagerDto = new TimesheetForManagerDto(costWorkUserInProject, timeWorkUserInProject);
            costWorkUserInProjectMap.put(projectUuid, timesheetForManagerDto);
        }
        return costWorkUserInProjectMap;
    }

    public Set<UUID> createProjectUuidList(List<TimesheetDto> timesheetDtoList) {
        return timesheetDtoList.stream()
                .map(TimesheetDto::getProjectUuid)
                .collect(toSet());
    }
}
