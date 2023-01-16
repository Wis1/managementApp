package com.example.demo.user.service;

import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserForm;
import com.example.demo.user.enums.UserRole;

import java.util.List;
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
                .salaryPerHour(35)
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
                .salaryPerHour(48)
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
                .salaryPerHour(120)
                .build();
    }

    public static List<User> createUserList() {
        return List.of(
                new User(1L, UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"), "poul", "bulek", "korek", UserRole.ADMINISTRATOR, "PACH", "antyghin@gmail.com", 68),
                new User(2L, UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c45"), "poulsen", "bulek", "korek", UserRole.MANAGER, "PACH", "antyghin@gmail.com", 68),
                new User(3L, UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c88"), "poul", "bulek", "korek", UserRole.EMPLOYEE, "PACH", "antyghin@gmail.com", 50));
    }
}
