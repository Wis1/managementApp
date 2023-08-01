package com.example.demo.timesheet.domain;

import com.example.demo.project.domain.Project;
import com.example.demo.projectuser.domain.ProjectUsers;
import com.example.demo.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.UUID;

@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Timesheet")
@Table(name = "timesheet")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timesheet_generator")
    @SequenceGenerator(name = "timesheet_generator", sequenceName = "timesheet_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private UUID uuid = UUID.randomUUID();

    @Column(name = "start_user_in_project")
    private LocalDateTime startUserInProject;

    @Column(name = "end_user_in_project")
    private LocalDateTime endUserInProject;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectUsers projectUsers;
}
