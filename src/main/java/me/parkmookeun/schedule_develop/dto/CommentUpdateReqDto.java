package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class CommentUpdateReqDto {
    private final String contents;

    public CommentUpdateReqDto(String contents) {
        this.contents = contents;
    }
}
