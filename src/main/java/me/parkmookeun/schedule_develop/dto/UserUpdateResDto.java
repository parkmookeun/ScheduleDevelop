package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;
import me.parkmookeun.schedule_develop.entity.User;

import java.time.LocalDateTime;

@Getter
public class UserUpdateResDto {

    private final Long id;
    private final String email;
    private final String username;
    private final LocalDateTime modifiedAt;

    public UserUpdateResDto(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.modifiedAt = user.getModifiedAt();
    }
}
