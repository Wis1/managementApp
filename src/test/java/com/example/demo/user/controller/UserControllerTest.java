package com.example.demo.user.controller;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.enums.UserRole;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {


    private static final UserService userService = Mockito.mock(UserService.class);

    @InjectMocks
    private UserController userController;

    private List<UserDto> userDtoList;

    @BeforeEach
    void createUserDtoList() {

        this.userDtoList = List.of(
                new UserDto(UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"), "poul", "bulek", "korek", UserRole.ADMINISTRATOR, "PACH", "antyghin@gmail.com", 68),
                new UserDto(UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c45"), "poulsen", "bulek", "korek", UserRole.MANAGER, "PACH", "antyghin@gmail.com", 68),
                new UserDto(UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c88"), "poul", "bulek", "korek", UserRole.EMPLOYEE, "PACH", "antyghin@gmail.com", 68));
    }

    @Test
    void shouldGetAllUsers() {

        //Given
        when(userService.getAllUsers()).thenReturn(userDtoList);
        //When
        List<UserDto> userDtoList1 = userController.getAllUsers();
        //Then
        assertEquals(userDtoList, userDtoList1);
        assertEquals(userDtoList1.size(), 3);
    }

    @Test
    void shouldGetUserWithUuid() {

        //Given
        UserDto userDto = new UserDto((UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33")), "poul", "bulek", "korek", UserRole.ADMINISTRATOR, "PACH", "antyghin@gmail.com", 68);
        when(userService.getUser(UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"))).thenReturn(userDto);
        //When
        UserDto userDto1 = userController.getUser(UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"));

        //Then
        assertEquals(userDto1, userDto);
        assertEquals(userDto1.getUserRole(), UserRole.ADMINISTRATOR);
    }

    @Test
    void shouldGetUserByExistLogin() {

        //Given
        UserDto userDto = new UserDto((UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33")), "poul", "bulek", "korek", UserRole.ADMINISTRATOR, "PACH", "antyghin@gmail.com", 68);
        when(userService.getUserByLogin((UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33")), "poul")).thenReturn(userDto);
        //When
        UserDto userDto1 = userController.getUserByLogin(UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"), "poul");
        //Then
        assertNotNull(userDto1);
    }

    @Test
    void shouldGetNullWhenGetByNotExistLogin() {

        //Given
        UserDto userDto = new UserDto((UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33")), "poul", "bulek", "korek", UserRole.ADMINISTRATOR, "PACH", "antyghin@gmail.com", 68);
        when(userService.getUserByLogin((UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33")), "poul")).thenReturn(userDto);
        //When
        UserDto userDto1 = userController.getUserByLogin(UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"), "pol");
        //Then
        assertNull(userDto1);
    }
}