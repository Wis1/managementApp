package com.example.demo.project.controller;

import com.example.demo.project.dto.ProjectDto;
import com.example.demo.project.dto.ProjectForm;
import com.example.demo.project.dto.ProjectSearch;
import com.example.demo.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<ProjectDto> getProjects(@RequestParam UUID uuid) {
        return projectService.getProjects(uuid);
    }

    @GetMapping("/filter")
    public Page<ProjectDto> filterProjectByCriteria(@RequestParam UUID uuid,
                                                    @RequestBody ProjectSearch projectSearch) {
        return projectService.filterByCriteria(uuid, projectSearch);
    }


    @PostMapping
    public void addNewProject(@RequestParam UUID uuid, @RequestBody ProjectForm projectForm) {
        projectService.addNewProject(uuid, projectForm);
    }

    @PutMapping("/projects/{projectUuid}/users/{userUuid}")
    public void addUserToProject(@RequestParam UUID uuid,
                                 @PathVariable UUID userUuid,
                                 @PathVariable UUID projectUuid) {
        projectService.addUserToProject(uuid, userUuid, projectUuid);
    }

    @DeleteMapping("/projects/{projectUuid}/users/{userUuid}")
    public void removeUserFromProject(@RequestParam UUID uuid,
                                      @PathVariable UUID userUuid,
                                      @PathVariable UUID projectUuid) {
        projectService.removeUserFromProject(uuid, userUuid, projectUuid);
    }

}
