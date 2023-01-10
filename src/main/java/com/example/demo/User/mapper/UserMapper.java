package com.example.demo.User.mapper;

import com.example.demo.User.domain.User;
import com.example.demo.User.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;


public class UserMapper {

    public static UserDto mapToUserDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getUuid(),
                user.getLogin(),
                user.getLastname(),
                user.getFirstname(),
                user.getUserRole(),
                user.getPassword(),
                user.getEmail(),
                user.getSalaryPerHour()
        );
    }

    public static User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getUuid(),
                userDto.getLogin(),
                userDto.getLastname(),
                userDto.getFirstname(),
                userDto.getUserRole(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getSalaryPerHour()
        );
    }

    public static List<UserDto> mapToListUserDto(final List<User> userList){
        return userList.stream()
                .map(u->new UserDto(
                        u.getId(),
                        u.getUuid(),
                        u.getLogin(),
                        u.getLastname(),
                        u.getFirstname(),
                        u.getUserRole(),
                        u.getPassword(),
                        u.getEmail(),
                        u.getSalaryPerHour()
                ))
                .collect(Collectors.toList());
    }

}
