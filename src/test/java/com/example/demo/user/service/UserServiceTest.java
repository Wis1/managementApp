package com.example.demo.user.service;

import com.example.demo.exception.UserIsAlreadyExists;
import com.example.demo.exception.UserIsNotAdministrator;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserForm;
import com.example.demo.user.enums.UserRole;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final UserRepository userRepository = Mockito.mock(UserRepository.class);

    @InjectMocks
    private UserService userService;

    private List<User> userList;

    @BeforeEach
    void createUserDtoList() {

        this.userList = List.of(
                new User(1L, UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"), "poul", "bulek", "korek", UserRole.ADMINISTRATOR, "PACH", "antyghin@gmail.com", 68),
                new User(2L, UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c45"), "poulsen", "bulek", "korek", UserRole.MANAGER, "PACH", "antyghin@gmail.com", 68),
                new User(3L, UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c88"), "poul", "bulek", "korek", UserRole.EMPLOYEE, "PACH", "antyghin@gmail.com", 50));
    }

    @Test
    void shouldThrowUserIsNotAdministratorWhenAddNewUserByManager() {

        //Given
        UserForm userForm = new UserForm("poul", "bilek", "cor", UserRole.EMPLOYEE, "kilo", "antygn@gmail.com", 79);
        User user = new User(7L, UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"), "poul", "bulek", "korek", UserRole.MANAGER, "PACH", "antyghin@gmail.com", 68);
        UUID uuidAdmin = (UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"));
        when(userRepository.findByUuid(UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"))).thenReturn(Optional.of(user));

        //When & Then
        assertThrows(UserIsNotAdministrator.class, () -> userService.saveUser(uuidAdmin, userForm));

    }

    @Test
    void shouldThrowsUserNotFoundExceptionWhenAddNewUserByNotExistUuid() {

        UserForm userForm = new UserForm("poul", "bilek", "cor", UserRole.EMPLOYEE, "kilo", "antygn@gmail.com", 79);
        User user = new User(7L, UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"), "poul", "bulek", "korek", UserRole.MANAGER, "PACH", "antyghin@gmail.com", 68);
        UUID uuidAdmin = (UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107888"));
        when(userRepository.findByUuid(UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"))).thenReturn(Optional.of(user));

        assertThrows(UserNotFoundException.class, () -> userService.saveUser(uuidAdmin, userForm));

    }

    @Test
    void shouldDisplayCorrectMessageExceptionWhenUserIsNotAdministrator() {
        UserForm userForm = new UserForm("poul", "bilek", "cor", UserRole.EMPLOYEE, "kilo", "antygn@gmail.com", 79);
        User user = new User(7L, UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"), "poul", "bulek", "korek", UserRole.MANAGER, "PACH", "antyghin@gmail.com", 68);
        UUID uuidAdmin = (UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"));
        when(userRepository.findByUuid(UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"))).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(UserIsNotAdministrator.class, () -> userService.saveUser(uuidAdmin, userForm));
        String expectedMessage = ("User with this uuid: " + uuidAdmin + " is not administrator");
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDisplayCorrectMessageExceptionWhenAddNewUserAndLoginAlreadyExists() {

        //Given
        User adminUser = new User(7L, UUID.fromString("110841e3-e6fb-4191-8fd8-5674a5107c33"), "poul", "bulek", "korek", UserRole.ADMINISTRATOR, "PACH", "antyghin@gmail.com", 68);
        UserForm userForm = new UserForm("poul", "bilek", "cor", UserRole.EMPLOYEE, "kilo", "antygn@gmail.com", 79);
        when(userRepository.findByUuid(adminUser.getUuid())).thenReturn(Optional.of(adminUser));
        when(userRepository.existsByLogin("poul")).thenReturn(true);

        //When
        RuntimeException exception = assertThrows(UserIsAlreadyExists.class, () -> userService.addNewUser(adminUser.getUuid(), userForm));
        String expectedMessage = ("User with this login: " + "poul" + " is already exists");

        //Then
        assertEquals(expectedMessage, exception.getMessage());


    }

    @Test
    void shouldGetUserList() {

        //Given
        when(userRepository.findAll()).thenReturn(userList);

        //When & Then
        assertEquals(UserMapper.mapToListUserDto(userList), userService.getAllUsers());

    }
}