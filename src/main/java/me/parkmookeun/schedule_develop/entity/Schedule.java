package me.parkmookeun.schedule_develop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.parkmookeun.schedule_develop.dto.ScheduleRequestDto;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseEntity + 일정아이디,일정제목,일정내용,유저아이디,일정 댓글목록
 */
@Getter
@Entity
@NoArgsConstructor
@Table(name = "schedule")
public class Schedule extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    public Schedule(ScheduleRequestDto dto){
        title = dto.getTitle();
        contents = dto.getContents();
    }
}
