package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentUpdateResDto {
    private final Long id;
    private final String contents;
    private final LocalDateTime modifiedAt;

    public CommentUpdateResDto(Long id, String contents, LocalDateTime modifiedAt) {
        this.id = id;
        this.contents = contents;
        this.modifiedAt = modifiedAt;
    }
}
