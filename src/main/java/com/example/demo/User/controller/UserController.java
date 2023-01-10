package com.example.demo.User.controller;

import com.example.demo.User.dto.UserDto;
import com.example.demo.User.mapper.UserMapper;
import com.example.demo.User.service.UserService;
import com.example.demo.exception.UserIsNotAdministrator;
import com.example.demo.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;
    @GetMapping
    public List<UserDto> getAllUsers(){
        return UserMapper.mapToListUserDto(userService.getAllUsers());

    }

    @GetMapping(value = "/{uuid}")
    public UserDto getUser(@PathVariable UUID uuid) throws UserNotFoundException {
        return UserMapper.mapToUserDto(userService.getUser(uuid));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/addUser/{adminUuid}")
    public void addUser(@PathVariable UUID adminUuid, @RequestBody UserDto userDto) throws UserNotFoundException, UserIsNotAdministrator {
        if (userService.checkUserToAdmin(adminUuid))
                userService.addNewUser(userDto);
        else throw new UserIsNotAdministrator();
    }
}
