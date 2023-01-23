package com.example.demo.project.mapper;

import com.example.demo.project.domain.Project;
import com.example.demo.project.dto.ProjectDto;
import com.example.demo.project.dto.ProjectForm;
import com.example.demo.user.mapper.UserMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static Project mapToProject(final ProjectForm projectForm) {
        return Project.builder()
                .name(projectForm.getName())
                .description(projectForm.getDescription())
                .startProject(projectForm.getStartProject())
                .endProject(projectForm.getEndProject())
                .budgetProject(projectForm.getBudgetProject())
                .actualUsedBudget(BigDecimal.ZERO)
                .percentageBudgetUsed(BigDecimal.ZERO)
                .build();
    }

    public static ProjectDto mapToProjectDto(final Project project) {
        return ProjectDto.builder()
                .uuid(project.getUuid())
                .name(project.getName())
                .description(project.getDescription())
                .startProject(project.getStartProject())
                .endProject(project.getEndProject())
                .budgetProject(project.getBudgetProject())
                .userList(UserMapper.mapToListUserDto(project.getUserList()))
                .build();
    }

    public static List<ProjectDto> mapToProjectListDto(final List<Project> userList) {
        return userList.stream()
                .map(p -> new ProjectDto(
                        p.getUuid(),
                        p.getName(),
                        p.getDescription(),
                        p.getStartProject(),
                        p.getEndProject(),
                        p.getBudgetProject(),
                        p.getUserList().stream()
                                .map(UserMapper::mapToUserDto)
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
    }
}
