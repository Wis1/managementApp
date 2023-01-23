package com.example.demo.project.domain;

import com.example.demo.projectuser.domain.ProjectUsers;
import com.example.demo.projectuser.domain.ProjectUsersId;
import com.example.demo.timesheet.domain.Timesheet;
import com.example.demo.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Getter
@Setter
@Entity(name = "Projects")
@Table(name = "projects")
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Builder.Default
    @Column(name = "uuid", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private UUID uuid = UUID.randomUUID();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_project")
    private LocalDateTime startProject;

    @Column(name = "end_project")
    private LocalDateTime endProject;

    @Column(name = "budget_project")
    private BigDecimal budgetProject;

    @Column
    private BigDecimal percentageBudgetUsed;

    @Column(nullable = false)
    private BigDecimal actualUsedBudget= BigDecimal.ZERO;

    @ManyToMany(cascade =
            {CascadeType.MERGE,
                    CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(name = "project_user",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userList = new HashSet<>();

    public void addUser(User user) {
        userList.add(user);
        user.getProjectList().add(this);
    }

    public void removeUser(User user) {
        userList.remove(user);
        user.getProjectList().remove(this);
    }

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Timesheet> timesheetSet= new HashSet<>();

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ProjectUsers> users= new HashSet<>();

    public void addUsers(User user) {
        ProjectUsers projectUsers= new ProjectUsers(this, user);
        projectUsers.setId(new ProjectUsersId(getId(), user.getId()));
        projectUsers.setStartUserInProjectFrom(LocalDateTime.now());
        projectUsers.setEndUserInProjectTo(LocalDateTime.now());
        users.add(projectUsers);
        user.getProjects().add(projectUsers);
    }

    public void removeUsers(User user) {
        for (Iterator<ProjectUsers> iterator= users.iterator();
        iterator.hasNext(); ) {
            ProjectUsers projectUsers= iterator.next();

            if (projectUsers.getProject().equals(this) && projectUsers.getUser().equals(user)) {
                iterator.remove();
                projectUsers.getUser().getProjects().remove(projectUsers);
                projectUsers.setProject(null);
                projectUsers.setUser(null);
            }
        }
    }
}
