package com.example.demo.user.repository;

import com.example.demo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);


}
