package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;

@Getter
public class ScheduleUpdateReqDto {
    private final String title;
    private final String contents;

    public ScheduleUpdateReqDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
