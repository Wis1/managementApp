package com.example.demo.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProjectSearch {

    private String name;

    private LocalDateTime startProject;

    private LocalDateTime endProject;

    private List<UUID> userUuidList;

    private Boolean overBudget;
}
