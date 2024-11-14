package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long userId;
    private final String email;
    private final String username;


    public UserResponseDto(Long userId, String email, String username) {
        this.userId = userId;
        this.email = email;
        this.username = username;
    }
}
