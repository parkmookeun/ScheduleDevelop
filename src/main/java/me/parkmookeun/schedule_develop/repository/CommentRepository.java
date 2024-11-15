package me.parkmookeun.schedule_develop.repository;

import me.parkmookeun.schedule_develop.entity.Comment;
import me.parkmookeun.schedule_develop.exception.ScheduleNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new ScheduleNotFoundException(id+"에 해당하는 댓글이 없습니다!"));
    }

    List<Comment> findAllBySchedule_ScheduleId(Long scheduleId);

}
