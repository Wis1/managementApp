package com.example.demo.timesheet.service;

import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserOrProjectNotFoundException;
import com.example.demo.project.domain.Project;
import com.example.demo.project.repository.ProjectRepository;
import com.example.demo.projectuser.domain.ProjectUsers;
import com.example.demo.projectuser.repository.ProjectUsersRepository;
import com.example.demo.timesheet.domain.Timesheet;
import com.example.demo.timesheet.dto.TimesheetDto;
import com.example.demo.timesheet.dto.TimesheetForm;
import com.example.demo.timesheet.mapper.TimesheetMapper;
import com.example.demo.timesheet.repository.TimesheetRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public void addNewTimesheet(UUID uuid, TimesheetForm timesheetForm) {

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));

        Project project = projectRepository.findByUuid(timesheetForm.getProjectUuid())
                .orElseThrow(() -> new ProjectNotFoundException(timesheetForm.getProjectUuid()));
        Timesheet timesheet = new Timesheet();

        ProjectUsers projectUsers = projectUsersRepository.findByProjectIdAndUserId(project.getId(), user.getId())
                .orElseThrow(UserOrProjectNotFoundException::new);

        timesheet.setProjectUsers(projectUsers);


        if (project.getEndProject().isAfter(timesheetForm.getStartUserInProject())) {
            timesheet.setStartUserInProject(timesheetForm.getStartUserInProject());
        }

        if (project.getEndProject().isAfter(timesheetForm.getEndUserInProject())) {
            timesheet.setEndUserInProject(timesheetForm.getEndUserInProject());
        }

        project.setActualUsedBudget(project.getActualUsedBudget().add(calculateCostOfWorkingTime(user, timesheet)));
        project.setPercentageBudgetUsed(project.getActualUsedBudget().multiply(new BigDecimal(100))
                .divide(project.getBudgetProject()).setScale(2, RoundingMode.HALF_UP));
        timesheetRepository.save(timesheet);
    }

    public List<TimesheetDto> getTimesheetForUser(UUID userUuid) {
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new UserNotFoundException(userUuid));

        ProjectUsers projectUsers = projectUsersRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UserNotFoundException(userUuid));

        return TimesheetMapper.mapToListTimesheetDto(timesheetRepository.findByProjectUsers(projectUsers));
    }

    public BigDecimal calculateCostOfWorkingTime(User user, Timesheet timesheet) {
        BigDecimal workingTime= new BigDecimal(Duration.between(timesheet.getStartUserInProject(),
                                                timesheet.getEndUserInProject()).toHours());

        return workingTime.multiply(user.getSalaryPerHour());
    }
}
