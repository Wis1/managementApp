package com.example.demo.user.dto;

import com.example.demo.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserSearch {
    private String login;
    private String lastname;
    private String firstname;
    private UserRole userRole;
    private Integer salaryMin;
    private Integer salaryMax;
}

