package com.example.demo.User.service;

import com.example.demo.User.domain.User;
import com.example.demo.User.dto.UserDto;
import com.example.demo.User.dto.UserForm;
import com.example.demo.User.enums.UserRole;
import com.example.demo.User.mapper.UserMapper;
import com.example.demo.User.repository.UserRepository;
import com.example.demo.exception.UserIsAlreadyExists;
import com.example.demo.exception.UserIsNotAdministrator;
import com.example.demo.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return UserMapper.mapToListUserDto(userRepository.findAll());
    }

    public UserDto getUser(UUID uuid) throws UserNotFoundException {
        return UserMapper.mapToUserDto(userRepository.findByUuid(uuid).orElseThrow(UserNotFoundException::new));
    }

    public void addNewUser(UUID uuid, UserForm userForm) throws UserNotFoundException, UserIsNotAdministrator, UserIsAlreadyExists {
        if (checkLoginExists(userForm.getLogin()))
            throw new UserIsAlreadyExists();
        if (checkUserExistsAndIsAdmin(uuid))
            userRepository.save(UserMapper.mapToUser(userForm));
        else throw new UserIsNotAdministrator();
    }

    public boolean checkUserExistsAndIsAdmin(UUID adminUuid) throws UserNotFoundException {
        User user = userRepository.findByUuid(adminUuid).orElseThrow(UserNotFoundException::new);
        return user.getUserRole().equals(UserRole.ADMINISTRATOR);
    }

    public void saveUser(UUID uuid, UserForm userForm) throws UserNotFoundException, UserIsNotAdministrator {
        if (checkUserExistsAndIsAdmin(uuid)) {
            userRepository.save(UserMapper.mapToUser(userForm));
        } else throw new UserIsNotAdministrator();
    }

    public void deleteUser(UUID adminUuid, UUID userUUID) throws UserIsNotAdministrator, UserNotFoundException {
        if (checkUserExistsAndIsAdmin(adminUuid))
            userRepository.deleteByUuid(userUUID);
        else throw new UserIsNotAdministrator();
    }

    public UserDto getUserByLogin(UUID uuid, String login) throws UserNotFoundException, UserIsNotAdministrator {
        if (checkUserExistsAndIsAdmin(uuid))
            return UserMapper.mapToUserDto(userRepository.getUserByLogin(login).orElseThrow(UserNotFoundException::new));
        else throw new UserIsNotAdministrator();
    }

    public boolean checkLoginExists(String login) {
        return userRepository.existsByLogin(login);
    }
}
