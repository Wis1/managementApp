package com.example.demo.user.controller;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.enums.UserRole;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.service.InitUser;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {


    private static final UserService userService = Mockito.mock(UserService.class);

    @InjectMocks
    private UserController userController;

    @Test
    void shouldGetAllUsers() {

        //Given
        List<UserDto> list = UserMapper.mapToListUserDto(InitUser.createUserList());
        when(userService.getAllUsers()).thenReturn(list);
        //When
        List<UserDto> userDtoList = userController.getAllUsers();
        //Then
        assertEquals(list, userDtoList);
        assertEquals(userDtoList.size(), 3);
    }

    @Test
    void shouldGetUserWithUuid() {

        //Given
        UserDto userAdmin = UserMapper.mapToUserDto(InitUser.createUserAdmin());
        when(userService.getUser(userAdmin.getUuid())).thenReturn(userAdmin);
        //When
        UserDto userDto = userController.getUser(userAdmin.getUuid());
        //Then
        assertEquals(userDto, userAdmin);
        assertEquals(userDto.getUserRole(), UserRole.ADMINISTRATOR);
    }

    @Test
    void shouldGetUserByExistLogin() {

        //Given
        UserDto userAdmin = UserMapper.mapToUserDto(InitUser.createUserAdmin());
        when(userService.getUserByLogin(userAdmin.getUuid(), "poul")).thenReturn(userAdmin);
        //When
        UserDto userDto = userController.getUserByLogin(userAdmin.getUuid(), "poul");
        //Then
        assertNotNull(userDto);
    }

    @Test
    void shouldGetNullWhenGetByNotExistLogin() {

        //Given
        UserDto userAdmin = UserMapper.mapToUserDto(InitUser.createUserAdmin());
        when(userService.getUserByLogin(userAdmin.getUuid(), "poul")).thenReturn(userAdmin);
        //When
        UserDto userDto = userController.getUserByLogin(userAdmin.getUuid(), "pol");
        //Then
        assertNull(userDto);
    }
}