package com.example.demo.project.repository;

import com.example.demo.project.domain.Project;
import com.example.demo.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID>, JpaSpecificationExecutor<Project> {

    @EntityGraph(attributePaths = {"userList"})
    Optional<Project> findByUuid(UUID projectUuid);

    @Override
    @EntityGraph(attributePaths = {"userList"})
    Page<Project> findAll(@Nullable Specification<Project> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"userList"})
    Optional<Project> findByUuidAndUserList(UUID projectUuid, User user);


}
