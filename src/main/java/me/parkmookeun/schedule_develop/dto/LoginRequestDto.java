package me.parkmookeun.schedule_develop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @Email
    @Pattern(message = "이메일 형식에 맞게 입력하세요!", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    private final String email;

    @NotBlank
    private final String password;


    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
