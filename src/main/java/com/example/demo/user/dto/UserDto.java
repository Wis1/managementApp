package com.example.demo.user.dto;

import com.example.demo.user.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@Builder
public class UserDto {

    private final UUID uuid;
    private final String login;
    private final String lastname;
    private final String firstname;
    private final UserRole userRole;
    private final String password;
    private final String email;
    private final int salaryPerHour;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final UserDto userDto)) return false;

        return Objects.equals(uuid, userDto.uuid);
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
