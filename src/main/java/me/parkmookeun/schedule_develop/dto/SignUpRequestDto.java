package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private final String email;
    private final String username;
    private final String password;


    public SignUpRequestDto(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
