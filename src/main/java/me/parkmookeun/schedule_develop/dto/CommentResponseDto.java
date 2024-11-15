package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;
import me.parkmookeun.schedule_develop.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String contents;
    private final LocalDateTime createdAt;

    public CommentResponseDto(Long id, String contents, LocalDateTime createdAt) {
        this.id = id;
        this.contents = contents;
        this.createdAt = createdAt;
    }
    public CommentResponseDto(Comment comment){
        this.id = comment. getId();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
    }
}
