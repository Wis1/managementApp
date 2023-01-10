package com.example.demo.User.service;

import com.example.demo.User.domain.User;
import com.example.demo.User.dto.UserDto;
import com.example.demo.User.mapper.UserMapper;
import com.example.demo.User.repository.UserRepository;
import com.example.demo.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(UUID uuid) throws UserNotFoundException {
        return userRepository.findByUuid(uuid).orElseThrow(UserNotFoundException::new);
    }

    public void addNewUser(UserDto userDto) {
        userRepository.save(UserMapper.mapToUser(userDto));
    }

    public boolean checkUserToAdmin(UUID adminUuid) throws UserNotFoundException {
        User user= userRepository.findByUuid(adminUuid).orElseThrow(UserNotFoundException::new);
        return user.getUserRole().toString().equals("ADMINISTRATOR");
    }
}
