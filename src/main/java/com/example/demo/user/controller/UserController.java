package com.example.demo.user.controller;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.dto.UserForm;
import com.example.demo.user.dto.UserSearch;
import com.example.demo.user.service.UserService;
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

    @GetMapping(value = "/uuid/{uuid}")
    public UserDto getUser(@PathVariable UUID uuid) {
        return userService.getUser(uuid);
    }

    @GetMapping(value = "/uuid/{uuid}/filter/{login}")
    public UserDto getUserByLogin(@PathVariable UUID uuid, @PathVariable String login) {
        return userService.getUserByLogin(uuid, login);
    }

    @PostMapping(value = "/uuid/{uuid}")
    public void addUser(@PathVariable UUID uuid, @Valid @RequestBody UserForm userForm) {
        userService.addNewUser(uuid, userForm);
    }

    @PutMapping(value = "/uuid/{uuid}")
    public void updateUser(@PathVariable UUID uuid, @RequestBody UserForm userForm) {
        userService.saveUser(uuid, userForm);
    }

    @Transactional
    @DeleteMapping(value = "/uuid/{adminUuid}/user/{userUuid}")
    public void deleteUser(@PathVariable(name = "adminUuid") UUID adminUuid, @PathVariable(name = "userUuid") UUID userUuid) {
        userService.deleteUser(adminUuid, userUuid);
    }

    @GetMapping(value = "/uuid//{adminUuid}")
    public List<UserDto> filterUserByCriteria(@PathVariable(name = "adminUuid") UUID adminUuid, UserSearch userSearch) {

        return userService.filterByCriteria(adminUuid, userSearch);
    }
}
