package com.example.demo.common;

import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.project.domain.Project;
import com.example.demo.project.repository.ProjectRepository;
import com.example.demo.project.service.ProjectService;
import com.example.demo.timesheet.dto.TimesheetDto;
import com.example.demo.timesheet.dto.TimesheetForManagerDto;
import com.example.demo.timesheet.service.TimesheetService;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.mapper.UserMapper;
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
public class ReportProjectMaker {

    private final TimesheetService timesheetService;
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ReportProjectDto projectInfo(UUID uuid, UUID projectUuid, LocalDateTime timeFrom, LocalDateTime timeTo, PeriodTime periodTime) {
        projectService.checkIsManagerOrAdministrator(uuid);

        Project project = projectRepository.findByUuid(projectUuid).
                orElseThrow(() -> new ProjectNotFoundException(projectUuid));

        List<TimesheetDto> timesheetDtoListInPeriod = new ArrayList<>();

        if (periodTime != null) {
            switch (periodTime) {
                case YEAR ->
                        timesheetDtoListInPeriod = filterTimesheetForProjectAndPeriod(projectUuid, PeriodTime.YEAR.returnDate(), LocalDateTime.now());
                case MONTH ->
                        timesheetDtoListInPeriod = filterTimesheetForProjectAndPeriod(projectUuid, PeriodTime.MONTH.returnDate(), LocalDateTime.now());
                case WEEK ->
                        timesheetDtoListInPeriod = filterTimesheetForProjectAndPeriod(projectUuid, PeriodTime.WEEK.returnDate(), LocalDateTime.now());
            }
        } else {
            timesheetDtoListInPeriod = filterTimesheetForProjectAndPeriod(projectUuid, timeFrom, timeTo);
        }

        Set<UserDto> users = createUsersSet(timesheetDtoListInPeriod);

        return ReportProjectDto.builder()
                .projectUuid(projectUuid)
                .usersInfoInProject(createMapWithUsersInProject(users, timesheetDtoListInPeriod))
                .costProject(calculateActualCostProject(timesheetDtoListInPeriod))
                .timeWorkInProject(calculateActualTimeInProject(timesheetDtoListInPeriod))
                .inBudget(isBudgetOver(project))
                .build();
    }

    public Long calculateActualTimeInProject(List<TimesheetDto> timesheets) {
        return timesheets.stream()
                .mapToLong(t -> Duration.between(t.getStartUserInProject(), t.getEndUserInProject()).toHours())
                .sum();
    }

    public BigDecimal calculateActualCostProject(List<TimesheetDto> timesheets) {
        return timesheets.stream()
                .map(t -> userRepository.findByUuid(t.getUserUuid()).get().getSalaryPerHour().
                        multiply(BigDecimal.valueOf(Duration.between(t.getStartUserInProject(), t.getEndUserInProject()).toHours())))
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    public List<TimesheetDto> filterTimesheetForProjectAndPeriod(UUID projectUuid, final LocalDateTime timeFrom, final LocalDateTime timeTo) {
        List<TimesheetDto> timesheets = timesheetService.getTimesheetForProject(projectUuid);
        List<TimesheetDto> timesheetDtoListInPeriod;
        if (timeFrom == null || timeTo == null) {
            timesheetDtoListInPeriod = timesheets;
        } else {
            timesheetDtoListInPeriod = timesheets.stream()
                    .filter(t -> t.getStartProject().isAfter(timeFrom))
                    .filter(t -> t.getEndProject().isBefore(timeTo))
                    .toList();
        }
        return timesheetDtoListInPeriod;
    }

    public Map<UserDto, TimesheetForManagerDto> createMapWithUsersInProject(Set<UserDto> userDtos, List<TimesheetDto> timesheetDtosInPeriod) {

        Map<UserDto, TimesheetForManagerDto> usersInProject = new HashMap<>();
        BigDecimal costWorkUserInProject;
        long timeWorkUserInProject;

        for (UserDto userDto : userDtos) {
            Set<TimesheetDto> timesheetDtoSet = timesheetDtosInPeriod.stream()
                    .filter(timesheetDto -> timesheetDto.getUserUuid().equals(userDto.getUuid()))
                    .collect(toSet());
            timeWorkUserInProject = timesheetDtoSet.stream()
                    .mapToLong(t -> Duration.between(t.getStartUserInProject(), t.getEndUserInProject()).toHours())
                    .sum();
            costWorkUserInProject = timesheetDtoSet.stream()
                    .map(t -> userDto.getSalaryPerHour().multiply(BigDecimal.valueOf(Duration.between(
                            t.getStartUserInProject(), t.getEndUserInProject()
                    ).toHours())))
                    .reduce(BigDecimal::add)
                    .orElseThrow();
            TimesheetForManagerDto timesheetForManagerDto = new TimesheetForManagerDto(costWorkUserInProject, timeWorkUserInProject);
            usersInProject.put(userDto, timesheetForManagerDto);
        }
        return usersInProject;
    }

    public Set<UserDto> createUsersSet(List<TimesheetDto> timesheetDtos) {
        return timesheetDtos.stream()
                .map(TimesheetDto::getUserUuid)
                .map(uuid -> UserMapper.mapToUserDto(userRepository.findByUuid(uuid).orElseThrow(() -> new UserNotFoundException(uuid))))
                .collect(toSet());
    }

    public Boolean isBudgetOver(Project project) {

        return project.getPercentageBudgetUsed().compareTo(BigDecimal.valueOf(100)) != 1;

    }
}
