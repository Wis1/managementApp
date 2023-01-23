package com.example.demo.projectuser.domain;

import com.example.demo.project.domain.Project;
import com.example.demo.user.domain.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "Project_users")
@Table(name = "project_users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectUsers {

    @EmbeddedId
    private ProjectUsersId id;

    @EqualsAndHashCode.Include
    private UUID uuid;

    private LocalDateTime startUserInProjectFrom;

    private LocalDateTime endUserInProjectTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    private Project project;

    public ProjectUsers(Project project, User user) {
        this.project= project;
        this.user= user;
        this.id= new ProjectUsersId(project.getId(), user.getId());
    }



}
