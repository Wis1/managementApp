package com.example.demo.user.dto;

import com.example.demo.user.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserForm {
    @NotBlank(message = "Login may not be blank")
    private String login;
    @NotBlank(message = "Lastname may not be blank")
    private String lastname;
    @NotBlank(message = "Firstname may not be blank")
    private String firstname;
    @NotNull(message = "UserRole may be only: ADMINISTRATOR, MANAGER, EMPLOYEE")
    private UserRole userRole;
    @NotBlank(message = "Password may not be blank")
    private String password;
    @Email
    @NotBlank(message = "Email may not be blank")
    private String email;
    @Positive
    private BigDecimal salaryPerHour;
}
