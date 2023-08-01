package com.example.demo.timesheet.service;

import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.exception.TimesheetIsAlreadyExistInThisTimeException;
import com.example.demo.exception.TimesheetIsNotFoundException;
import com.example.demo.exception.UserIsNotConnectWithProjectException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.project.domain.Project;
import com.example.demo.project.repository.ProjectRepository;
import com.example.demo.project.service.ProjectService;
import com.example.demo.projectuser.domain.ProjectUsers;
import com.example.demo.projectuser.repository.ProjectUsersRepository;
import com.example.demo.timesheet.domain.Timesheet;
import com.example.demo.timesheet.dto.TimesheetDto;
import com.example.demo.timesheet.dto.TimesheetForm;
import com.example.demo.timesheet.dto.TimesheetSearch;
import com.example.demo.timesheet.mapper.TimesheetMapper;
import com.example.demo.timesheet.repository.TimesheetRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectUsersRepository projectUsersRepository;
    private final ProjectService projectService;

    @Transactional
    public void addNewTimesheet(UUID uuid, TimesheetForm timesheetForm) {

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));

        Project project = projectRepository.findByUuid(timesheetForm.getProjectUuid())
                .orElseThrow(() -> new ProjectNotFoundException(timesheetForm.getProjectUuid()));
        Timesheet timesheet = new Timesheet();

        ProjectUsers projectUsers = projectUsersRepository.findByProjectIdAndUserId(project.getId(), user.getId())
                .orElseThrow(() -> new UserIsNotConnectWithProjectException(user.getUuid(), project.getUuid()));

        timesheet.setProjectUsers(projectUsers);

        if (!timesheetRepository.existTimeRecordBetweenDateTime(timesheetForm.getStartUserInProject(),
                timesheetForm.getEndUserInProject(), uuid)) {

            timesheet.setStartUserInProject(timesheetForm.getStartUserInProject());
            timesheet.setEndUserInProject(timesheetForm.getEndUserInProject());
        } else {
            throw new TimesheetIsAlreadyExistInThisTimeException();
        }
        timesheetRepository.save(timesheet);
        updateActualUsedBudgetAndPercentageBudgetUsed(project.getUuid(),
                calculateCostOfWorkingTime(user, timesheet), "add");
    }

    public List<TimesheetDto> getTimesheetForUser(UUID userUuid) {
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new UserNotFoundException(userUuid));

        List<ProjectUsers> projectUsers = projectUsersRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UserNotFoundException(userUuid));

        return TimesheetMapper.mapToListTimesheetDto(timesheetRepository.findByProjectUsersIn(projectUsers));
    }

    public List<TimesheetDto> getTimesheetForProject(UUID projectUuid) {
        Project project = projectRepository.findByUuid(projectUuid)
                .orElseThrow(() -> new ProjectNotFoundException(projectUuid));

        List<ProjectUsers> projectUsers = projectUsersRepository.findByProjectId(project.getId())
                .orElseThrow(() -> new ProjectNotFoundException(projectUuid));

        return TimesheetMapper.mapToListTimesheetDto(timesheetRepository.findByProjectUsersIn(projectUsers));
    }

    public BigDecimal calculateCostOfWorkingTime(User user, Timesheet timesheet) {
        BigDecimal workingTime = new BigDecimal(Duration.between(timesheet.getStartUserInProject(),
                timesheet.getEndUserInProject()).toHours());

        return workingTime.multiply(user.getSalaryPerHour());
    }

    public void updateActualUsedBudgetAndPercentageBudgetUsed(UUID projectUuid, BigDecimal changesAmount, String operation) {
        Project project = projectRepository.findByUuid(projectUuid)
                .orElseThrow(() -> new ProjectNotFoundException(projectUuid));

        switch (operation) {
            case "subtract" -> {
                project.setActualUsedBudget(project.getActualUsedBudget().subtract(changesAmount));
                project.setPercentageBudgetUsed(project.getActualUsedBudget().multiply(new BigDecimal(100))
                        .divide(project.getBudgetProject(), 2, RoundingMode.HALF_UP));
            }
            case "add" -> {
                project.setActualUsedBudget(project.getActualUsedBudget().add(changesAmount));
                project.setPercentageBudgetUsed(project.getActualUsedBudget().multiply(new BigDecimal(100))
                        .divide(project.getBudgetProject(), 2, RoundingMode.HALF_UP));
            }
        }
    }

    public void updateTimesheet(final UUID uuid, final TimesheetForm timesheetForm) {
        Timesheet timesheet = timesheetRepository.findByUuid(uuid).orElseThrow(
                () -> new TimesheetIsNotFoundException(uuid));

        updateActualUsedBudgetAndPercentageBudgetUsed(timesheet.getProjectUsers().getProject().getUuid(),
                calculateCostOfWorkingTime(timesheet.getProjectUsers().getUser(), timesheet), "subtract");

        timesheet.setStartUserInProject(null);
        timesheet.setEndUserInProject(null);
        timesheetRepository.save(timesheet);

        if (!timesheetRepository.existTimeRecordBetweenDateTime(timesheetForm.getStartUserInProject(),
                timesheetForm.getEndUserInProject(), uuid)) {
            timesheet.setStartUserInProject(timesheetForm.getStartUserInProject());
            timesheet.setEndUserInProject(timesheetForm.getEndUserInProject());
        } else {
            throw new TimesheetIsAlreadyExistInThisTimeException();
        }
        updateActualUsedBudgetAndPercentageBudgetUsed(timesheet.getProjectUsers().getProject().getUuid(),
                calculateCostOfWorkingTime(timesheet.getProjectUsers().getUser(), timesheet), "add");
        timesheetRepository.save(timesheet);
    }

    @Transactional
    public void deleteTimesheet(final UUID uuid) {
        Timesheet timesheet = timesheetRepository.findByUuid(uuid).orElseThrow(
                () -> new TimesheetIsNotFoundException(uuid));

        updateActualUsedBudgetAndPercentageBudgetUsed(timesheet.getProjectUsers().getProject().getUuid(),
                calculateCostOfWorkingTime(timesheet.getProjectUsers().getUser(), timesheet), "subtract");
        timesheetRepository.deleteByUuid(uuid);
    }

    public Page<TimesheetDto> filterByCriteria(UUID uuid, TimesheetSearch timesheetSearch) {
        projectService.checkIsManagerOrAdministrator(uuid);
        Pageable paging = PageRequest.of(0, 10);
        Specification<Timesheet> specification = new TimesheetSpecification(timesheetSearch);
        Page<Timesheet> page = timesheetRepository.findAll(specification, paging);

        return page.map(TimesheetMapper::mapToTimesheetDto);
    }
}
