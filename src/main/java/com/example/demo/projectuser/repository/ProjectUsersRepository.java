package com.example.demo.projectuser.repository;

import com.example.demo.projectuser.domain.ProjectUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectUsersRepository extends JpaRepository<ProjectUsers, Long> {

    Optional<ProjectUsers> findByProjectIdAndUserId(Long projectId, Long userId);

    Optional<ProjectUsers> findByUserId(Long userId);
}
