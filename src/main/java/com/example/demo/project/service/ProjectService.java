package com.example.demo.project.service;

import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.exception.UserIsAlreadyInProject;
import com.example.demo.exception.UserIsNotManager;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.project.domain.Project;
import com.example.demo.project.dto.ProjectDto;
import com.example.demo.project.dto.ProjectForm;
import com.example.demo.project.dto.ProjectSearch;
import com.example.demo.project.mapper.ProjectMapper;
import com.example.demo.project.repository.ProjectRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.enums.UserRole;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public void addNewProject(final UUID uuid, final ProjectForm projectForm) {

        checkIsManagerOrAdministrator(uuid);

        projectRepository.save(ProjectMapper.mapToProject(projectForm));
    }
    private void checkIsManagerOrAdministrator(final UUID uuid) {

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));

        if (!((UserRole.MANAGER).equals(user.getUserRole())||UserRole.ADMINISTRATOR.equals(user.getUserRole())))
            throw new UserIsNotManager(uuid);
    }

    public List<ProjectDto> getProjects(final UUID uuid) {

        checkIsManagerOrAdministrator(uuid);

        return ProjectMapper.mapToProjectListDto(projectRepository.findAll());
    }

    @Transactional
    public void addUserToProject(final UUID uuid, final UUID userUuid, final UUID projectUuid) {

        checkIsManagerOrAdministrator(uuid);

        Project project= projectRepository.findByUuid(projectUuid)
                .orElseThrow(()->new ProjectNotFoundException(projectUuid));

        User user=userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new UserNotFoundException(userUuid));

        projectRepository.findByUuidAndUserList(projectUuid,user)
                .ifPresent(u->{ throw new UserIsAlreadyInProject(userUuid, projectUuid);});

        project.addUser(user);
        projectRepository.save(project);
    }

    public void removeUserFromProject(final UUID uuid, final UUID userUuid, final UUID projectUuid) {
        checkIsManagerOrAdministrator(uuid);

        Project project= projectRepository.findByUuid(projectUuid)
                .orElseThrow(()->new ProjectNotFoundException(projectUuid));

        User user= userRepository.findByUuid(userUuid)
                .orElseThrow(()->new UserNotFoundException(userUuid));

        project.removeUser(user);
        projectRepository.save(project);
    }

    public Page<ProjectDto> filterByCriteria(UUID uuid, ProjectSearch projectSearch) {

        checkIsManagerOrAdministrator(uuid);

        Pageable paging= PageRequest.of(0,10);
        Specification<Project> specification= new ProjectSpecification(projectSearch);
        Page<Project> page= projectRepository.findAll(specification, paging);

        return page.map(ProjectMapper::mapToProjectDto);
    }
}
