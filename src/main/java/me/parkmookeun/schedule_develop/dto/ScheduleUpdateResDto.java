package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleUpdateResDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final LocalDateTime modifiedAt;

    public ScheduleUpdateResDto(Long id, String title, String contents, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.modifiedAt = modifiedAt;
    }
}
