package com.example.demo.User.dto;

import com.example.demo.User.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@Builder
public class UserDto {

    private final UUID uuid;
    private final String login;
    private final String lastname;
    private final String firstname;
    private final UserRole userRole;
    private final String password;
    private final String email;
    private final int salaryPerHour;


}
