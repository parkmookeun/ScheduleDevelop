package me.parkmookeun.schedule_develop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "schedule")
public class Schedule extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false, columnDefinition = "longtext")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
