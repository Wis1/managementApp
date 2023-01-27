package com.example.demo.projectuser.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectUsersId implements Serializable {

    @Column
    private Long projectId;

    @Column
    private Long userId;

    public ProjectUsersId(Long projectId, Long userId) {
        this.projectId = projectId;
        this.userId = userId;
    }
}
