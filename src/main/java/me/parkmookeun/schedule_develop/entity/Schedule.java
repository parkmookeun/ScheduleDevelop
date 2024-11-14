package me.parkmookeun.schedule_develop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.parkmookeun.schedule_develop.dto.ScheduleRequestDto;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "schedule")
public class Schedule extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Setter
    @Column(name = "title", nullable = false)
    private String title;

    @Setter
    @Column(name = "contents", nullable = false, columnDefinition = "longtext")
    private String contents;


    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Schedule(ScheduleRequestDto dto){
        title = dto.getTitle();
        contents = dto.getContents();
    }
}
