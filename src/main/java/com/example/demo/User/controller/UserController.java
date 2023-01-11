package com.example.demo.User.controller;

import com.example.demo.User.dto.UserDto;
import com.example.demo.User.dto.UserForm;
import com.example.demo.User.service.UserService;
import com.example.demo.exception.UserIsAlreadyExists;
import com.example.demo.exception.UserIsNotAdministrator;
import com.example.demo.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{uuid}")
    public UserDto getUser(@PathVariable UUID uuid) throws UserNotFoundException {
        return userService.getUser(uuid);
    }

    @GetMapping(value = "/uuid/{uuid}/filter/{login}")
    public UserDto getUserByLogin(
            @PathVariable UUID uuid,
            @PathVariable String login) throws UserNotFoundException, UserIsNotAdministrator {
        return userService.getUserByLogin(uuid, login);
    }

    @PostMapping(value = "/addUser/{uuid}")
    public void addUser(@PathVariable UUID uuid, @Valid @RequestBody UserForm userForm) throws UserNotFoundException, UserIsNotAdministrator, UserIsAlreadyExists {
        userService.addNewUser(uuid, userForm);
    }

    @PutMapping(value = "/updateUser/{uuid}")
    public void updateUser(@PathVariable UUID uuid, @RequestBody UserForm userForm) throws UserNotFoundException, UserIsNotAdministrator {
        userService.saveUser(uuid, userForm);
    }

    @Transactional
    @DeleteMapping(value = "/deleteUser/admin/{adminUuid}/user/{userUuid}")
    public void deleteUser(
            @PathVariable(name = "adminUuid") UUID adminUuid,
            @PathVariable(name = "userUuid") UUID userUuid) throws UserNotFoundException, UserIsNotAdministrator {
        userService.deleteUser(adminUuid, userUuid);
    }


}
