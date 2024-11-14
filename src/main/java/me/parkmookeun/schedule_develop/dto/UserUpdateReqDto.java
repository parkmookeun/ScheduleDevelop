package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;

@Getter
public class UserUpdateReqDto {
    private final String email;
    private final String username;

    public UserUpdateReqDto(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
