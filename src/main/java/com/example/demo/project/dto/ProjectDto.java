package com.example.demo.project.dto;

import com.example.demo.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class ProjectDto {

    private final UUID uuid;

    private final String name;

    private final String description;

    private final LocalDateTime startProject;

    private final LocalDateTime endProject;

    private final BigDecimal budgetProject;

    private final Set<UserDto> userList;

}
