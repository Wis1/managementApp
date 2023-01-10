package com.example.demo.User.dto;

import com.example.demo.User.enums.AppRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserDto {

    private Long id;
    private UUID uuid;
    private String login;
    private String lastname;
    private String firstname;
    private AppRoles userRole;
    private String password;
    private String email;
    private int salaryPerHour;





}
