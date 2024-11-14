package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;
import me.parkmookeun.schedule_develop.entity.User;

import java.time.LocalDateTime;

@Getter
public class SignUpResponseDto {
    private final Long userId;
    private final String email;
    private final String username;
    private final LocalDateTime createdAt;

    public SignUpResponseDto(User user){
        this.userId = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.createdAt = user.getCreatedAt();
    }

}
