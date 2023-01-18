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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private static final UserService userService = Mockito.mock(UserService.class);
    @InjectMocks
    private UserController userController;
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
}