package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class CommentCreateReqDto {

    private final String contents;

    public CommentCreateReqDto(String contents){
        this.contents = contents;
    }
}
