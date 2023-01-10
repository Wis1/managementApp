package com.example.demo.User.domain;


import com.example.demo.User.enums.AppRoles;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "uuid", unique = true, nullable = false)
    private UUID uuid=UUID.randomUUID();
    @Column(name="login", nullable= false)
    private String login;
    @Column(name= "lastname", nullable = false)
    private String lastname;
    @Column(name="firstname", nullable = false)
    private String firstname;
    @Column(name = "user_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AppRoles userRole;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "salary_per_hour", nullable = false)
    private int salaryPerHour;
}
