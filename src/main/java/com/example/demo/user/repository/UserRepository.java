package com.example.demo.user.repository;

import com.example.demo.user.domain.User;
import com.example.demo.user.enums.UserRole;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    boolean existsByLogin(String login);

    List<User> findAll(Specification<User> spec);

    Optional<User> findByUuidAndUserRole(UUID userUuid, UserRole userRole);
}
