package com.example.demo.user.dto;

import com.example.demo.user.enums.UserRole;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {

    @EqualsAndHashCode.Include
    private final UUID uuid;

    private final String login;

    private final String lastname;

    private final String firstname;

    private final UserRole userRole;

    private final String password;

    private final String email;

    private final BigDecimal salaryPerHour;

}
