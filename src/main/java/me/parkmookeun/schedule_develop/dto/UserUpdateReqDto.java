package me.parkmookeun.schedule_develop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateReqDto {

    @Email
    @Pattern(message = "이메일 형식에 맞게 입력하세요!", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    private final String email;

    @Size(max = 4, min = 1)
    private final String username;

    public UserUpdateReqDto(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
