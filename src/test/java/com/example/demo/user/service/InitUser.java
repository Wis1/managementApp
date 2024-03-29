package com.example.demo.user.service;

import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserForm;
import com.example.demo.user.enums.UserRole;

import java.math.BigDecimal;
import java.util.UUID;

public class InitUser {

    public static User createUserAdmin() {
        return User.builder()
                .id(7L)
                .uuid(UUID.randomUUID())
                .login("chris")
                .lastname("jackson")
                .firstname("peter")
                .userRole(UserRole.ADMINISTRATOR)
                .password("pass")
                .email("jackson@gmail.com")
                .salaryPerHour(BigDecimal.valueOf(35))
                .build();

    }

    public static User createUserManager() {
        return User.builder()
                .id(8L)
                .uuid(UUID.randomUUID())
                .login("riska")
                .lastname("jackson")
                .firstname("petersen")
                .userRole(UserRole.MANAGER)
                .password("pass")
                .email("jackson@gmail.com")
                .salaryPerHour(BigDecimal.valueOf(48))
                .build();

    }

    public static UserForm createUserForm() {
        return UserForm.builder()
                .login("olka")
                .lastname("jack")
                .firstname("kerrsen")
                .userRole(UserRole.MANAGER)
                .password("passover")
                .email("jackson@gmail.com")
                .salaryPerHour(BigDecimal.valueOf(120))
                .build();
    }
}
