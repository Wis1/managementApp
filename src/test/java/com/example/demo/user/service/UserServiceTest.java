package com.example.demo.user.service;

import com.example.demo.exception.UserIsAlreadyExists;
import com.example.demo.exception.UserIsNotAdministrator;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserForm;
import com.example.demo.user.dto.UserSearch;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.repository.UserRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private static final UserSpecification userSpecification = Mockito.mock(UserSpecification.class);

    @InjectMocks
    private UserService userService;

    @Test
    void shouldThrowUserIsNotAdministratorWhenAddSaveUserByManager() {

        //Given
        User userManager = InitUser.createUserManager();
        UserForm userForm = InitUser.createUserForm();
        when(userRepository.findByUuid(userManager.getUuid())).thenReturn(Optional.of(userManager));

        //When & Then
        assertThrows(UserIsNotAdministrator.class, () -> userService.saveUser(userManager.getUuid(), userForm));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenAddNewUserByNotExistUuid() {

        // Given
        User user = InitUser.createUserManager();
        UserForm userForm = InitUser.createUserForm();
        UUID uuid = UUID.randomUUID();
        when(userRepository.findByUuid(user.getUuid())).thenReturn(Optional.of(user));

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.saveUser(uuid, userForm));
    }

    @Test
    void shouldDisplayCorrectMessageExceptionWhenUserIsNotAdministrator() {

        // Given
        UserForm userForm = InitUser.createUserForm();
        User userManager = InitUser.createUserManager();
        when(userRepository.findByUuid(userManager.getUuid())).thenReturn(Optional.of(userManager));

        // When& Then
        RuntimeException exception = assertThrows(UserIsNotAdministrator.class, () -> userService.saveUser(userManager.getUuid(), userForm));
        String expectedMessage = (String.format("User with this uuid: %s is not administrator", userManager.getUuid()));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDisplayCorrectMessageExceptionWhenAddNewUserAndLoginAlreadyExists() {

        //Given
        User userAdmin = InitUser.createUserAdmin();
        UserForm userForm = InitUser.createUserForm();

        when(userRepository.findByUuid(userAdmin.getUuid())).thenReturn(Optional.of(userAdmin));
        when(userRepository.existsByLogin(userForm.getLogin())).thenReturn(true);


        RuntimeException exception = assertThrows(UserIsAlreadyExists.class, () -> userService.addNewUser(userAdmin.getUuid(), userForm));
        String expectedMessage = (String.format("User with this login: %s is already exists", userForm.getLogin()));

        //When & Then
        assertEquals(expectedMessage, exception.getMessage());


    }

    @Test
    void shouldGetUserList() {

        //Given
        List<User> userList = InitUser.createUserList();
        when(userRepository.findAll()).thenReturn(userList);

        //When & Then
        assertEquals(UserMapper.mapToListUserDto(userList), userService.getAllUsers());

    }

    @Test
    void shouldGetAllUserListWhenFilterWithoutFilters() {

        //Given
        User userAdmin = InitUser.createUserAdmin();
        List<User> userList = InitUser.createUserList();
        UserSearch userSearch = new UserSearch(null, null, null, null, null, null);
        when(userRepository.findByUuid(userAdmin.getUuid())).thenReturn(Optional.of(userAdmin));
        when(userRepository.findAll(userSpecification.getUsers(userSearch))).thenReturn(userList);

        //When & Then
        assertEquals(UserMapper.mapToListUserDto(userList), userService.filterByCriteria(userAdmin.getUuid(), userSearch));
    }

    @Test
    void shouldSaveUserWhenAddNewUser() {

        //Given
        UserForm userForm = InitUser.createUserForm();
        User userAdmin = InitUser.createUserAdmin();
        when(userRepository.findByUuid(userAdmin.getUuid())).thenReturn(Optional.of(userAdmin));
        when(userRepository.existsByLogin(userForm.getLogin())).thenReturn(false);
        userService.addNewUser(userAdmin.getUuid(), userForm);

        //When & Then
        verify(userRepository, times(1)).save(UserMapper.mapToUser(userForm));
    }
}