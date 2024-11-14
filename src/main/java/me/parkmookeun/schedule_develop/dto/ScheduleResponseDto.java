package me.parkmookeun.schedule_develop.dto;

import lombok.Getter;
import me.parkmookeun.schedule_develop.entity.Schedule;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private final Long scheduleId;
    private final String title;
    private final String contents;
    private final LocalDateTime creaatedAt;

    public ScheduleResponseDto(Long scheduleId, String title, String contents, LocalDateTime creaatedAt) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.contents = contents;
        this.creaatedAt = creaatedAt;
    }

    public ScheduleResponseDto(Schedule schedule){
        this.scheduleId = schedule.getScheduleId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.creaatedAt = schedule.getCreatedAt();
    }
}
