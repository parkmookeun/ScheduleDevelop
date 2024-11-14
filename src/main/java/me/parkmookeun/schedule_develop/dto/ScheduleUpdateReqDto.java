package me.parkmookeun.schedule_develop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleUpdateReqDto {

    @Size(max = 10, min = 1)
    private final String title;

    @NotBlank
    private final String contents;

    public ScheduleUpdateReqDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
