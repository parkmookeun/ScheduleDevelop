package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;
import me.parkmookeun.schedule_develop.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentCreateResDto {
    private final Long id;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public CommentCreateResDto(Long id, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
    public CommentCreateResDto(Comment comment){
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
