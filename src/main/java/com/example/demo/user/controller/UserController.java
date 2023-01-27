package com.example.demo.user.controller;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.dto.UserForm;
import com.example.demo.user.dto.UserSearch;
import com.example.demo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{uuid}")
    public UserDto getUser(@PathVariable UUID uuid) {
        return userService.getUser(uuid);
    }

    @GetMapping
    public Page<UserDto> filterUserByCriteria(@RequestParam(name = "adminUuid") UUID adminUuid,
                                              UserSearch userSearch,
                                              @RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                              @RequestParam(required = false, defaultValue = "login") String sortBy) {
        return userService.filterByCriteria(adminUuid, userSearch, pageNo, pageSize, sortBy);
    }

    @PostMapping
    public void addUser(@RequestParam UUID uuid, @Valid @RequestBody UserForm userForm) {
        userService.addNewUser(uuid, userForm);
    }

    @PutMapping
    public void updateUser(@RequestParam UUID uuid, @RequestBody UserForm userForm) {
        userService.saveUser(uuid, userForm);
    }

    @DeleteMapping("/{userUuid}")
    public void deleteUser(@RequestParam UUID adminUuid, @PathVariable(name = "userUuid") UUID userUuid) {
        userService.deleteUser(adminUuid, userUuid);
    }
}
