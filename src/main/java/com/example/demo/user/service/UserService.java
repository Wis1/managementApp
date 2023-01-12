package com.example.demo.user.service;

import com.example.demo.exception.UserIsAlreadyExists;
import com.example.demo.exception.UserIsNotAdministrator;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.user.domain.User;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.dto.UserForm;
import com.example.demo.user.dto.UserSearch;
import com.example.demo.user.enums.UserRole;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSpecification userSpecification;


    public List<UserDto> getAllUsers() {

        return UserMapper.mapToListUserDto(userRepository.findAll());
    }

    public UserDto getUser(UUID uuid) {

        return UserMapper.mapToUserDto(userRepository.findByUuid(uuid).orElseThrow(() -> new UserNotFoundException(uuid)));
    }

    public void addNewUser(UUID uuid, UserForm userForm) {

        checkUserExistsAndIsAdmin(uuid);
        checkLoginExists(userForm.getLogin());
        userRepository.save(UserMapper.mapToUser(userForm));
    }

    public void checkUserExistsAndIsAdmin(UUID adminUuid) {

        User user = userRepository.findByUuid(adminUuid).orElseThrow(() -> new UserNotFoundException(adminUuid));
        if (!user.getUserRole().equals(UserRole.ADMINISTRATOR)) throw new UserIsNotAdministrator(adminUuid);
    }

    public void saveUser(UUID uuid, UserForm userForm) {

        checkUserExistsAndIsAdmin(uuid);
        userRepository.save(UserMapper.mapToUser(userForm));
    }

    public void deleteUser(UUID adminUuid, UUID userUUID) {

        checkUserExistsAndIsAdmin(adminUuid);
        userRepository.deleteByUuid(userUUID);
    }

    public UserDto getUserByLogin(UUID uuid, String login) {

        checkUserExistsAndIsAdmin(uuid);
        return UserMapper.mapToUserDto(userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login)));
    }

    public void checkLoginExists(String login) {

        if (userRepository.existsByLogin(login)) throw new UserIsAlreadyExists(login);

    }

    public List<UserDto> filterByCriteria(UUID uuid, UserSearch userSearch){
        checkUserExistsAndIsAdmin(uuid);
        return UserMapper.mapToListUserDto(userRepository.findAll(userSpecification.getUsers(userSearch)));
    }
}
