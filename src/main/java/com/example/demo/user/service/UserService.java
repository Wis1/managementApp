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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto getUser(UUID uuid) {
        return userRepository.findByUuid(uuid)
                .map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new UserNotFoundException(uuid));
    }

    @Transactional
    public void addNewUser(UUID uuid, UserForm userForm) {
        checkIsAdmin(uuid);
        checkLoginExists(userForm.getLogin());
        userRepository.save(UserMapper.mapToUser(userForm));
    }

    public void checkIsAdmin(UUID adminUuid) {
        userRepository.findByUuidAndUserRole(adminUuid, UserRole.ADMINISTRATOR)
                .orElseThrow(() -> new UserIsNotAdministrator(adminUuid));
    }

    public void saveUser(UUID uuid, UserForm userForm) {
        checkIsAdmin(uuid);
        userRepository.save(UserMapper.mapToUser(userForm));
    }

    @Transactional
    public void deleteUser(UUID adminUuid, UUID userUUID) {
        checkIsAdmin(adminUuid);
        userRepository.deleteByUuid(userUUID);
    }

    public void checkLoginExists(String login) {
        if (userRepository.existsByLogin(login))
            throw new UserIsAlreadyExists(login);
    }

    public Page<UserDto> filterByCriteria(UUID uuid, UserSearch userSearch, final Integer pageNo,
                                          final Integer pageSize, final String sortBy) {
        checkIsAdmin(uuid);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Specification<User> specification = new UserSpecification(userSearch);
        Page<User> page = userRepository.findAll(specification, paging);
        return page.map(UserMapper::mapToUserDto);
    }
}
