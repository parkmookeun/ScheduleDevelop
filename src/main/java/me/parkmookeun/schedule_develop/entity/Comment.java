package me.parkmookeun.schedule_develop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BaseEntity + 아이디,댓글내용,일정아이디
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(columnDefinition = "longtext")
    private String contents;


    @Setter
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(String contents){
        this.contents =contents;
    }
}
