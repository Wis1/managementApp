package com.example.demo.project.dto;

import com.example.demo.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@Builder
public class ProjectDto {

    private final UUID uuid;
    private final String name;
    private final String description;
    private final LocalDateTime startProject;
    private final LocalDateTime endProject;
    private final BigDecimal budgetProject;
    private final List<User> userList;

}