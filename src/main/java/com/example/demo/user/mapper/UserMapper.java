package com.example.demo.user.mapper;

import com.example.demo.projectuser.domain.ProjectUsers;
import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.dto.UserForm;

import java.util.Set;

import static java.util.stream.Collectors.toSet;


public class UserMapper {

    public static UserDto mapToUserDto(final User user) {
        return UserDto.builder()
                .uuid(user.getUuid())
                .login(user.getLogin())
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .userRole(user.getUserRole())
                .password(user.getPassword())
                .email(user.getEmail())
                .salaryPerHour(user.getSalaryPerHour())
                .build();
    }

    public static User mapToUser(final UserDto userDto) {
        return User.builder()
                .uuid(userDto.getUuid())
                .login(userDto.getLogin())
                .lastname(userDto.getLastname())
                .firstname(userDto.getFirstname())
                .userRole(userDto.getUserRole())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .salaryPerHour(userDto.getSalaryPerHour())
                .build();
    }

    public static User mapToUser(final UserForm userForm) {
        return User.builder()
                .login(userForm.getLogin())
                .lastname(userForm.getLastname())
                .firstname(userForm.getFirstname())
                .userRole(userForm.getUserRole())
                .password(userForm.getPassword())
                .email(userForm.getEmail())
                .salaryPerHour(userForm.getSalaryPerHour())
                .build();
    }

    public static Set<UserDto> mapToListUserDto(final Set<User> users) {
        return users.stream()
                .map(u -> new UserDto(
                        u.getUuid(),
                        u.getLogin(),
                        u.getLastname(),
                        u.getFirstname(),
                        u.getUserRole(),
                        u.getPassword(),
                        u.getEmail(),
                        u.getSalaryPerHour()
                ))
                .collect(toSet());
    }

    public static Set<User> mapToSetUser(final Set<ProjectUsers> projectUsers) {
        return projectUsers.stream().map(ProjectUsers::getUser).collect(toSet());
    }
}
