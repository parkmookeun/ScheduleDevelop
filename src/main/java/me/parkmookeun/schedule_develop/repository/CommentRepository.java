package me.parkmookeun.schedule_develop.repository;

import me.parkmookeun.schedule_develop.entity.Comment;
import me.parkmookeun.schedule_develop.exception.ScheduleNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new ScheduleNotFoundException(id+"에 해당하는 댓글이 없습니다!"));
    }

    Page<Comment> findAllBySchedule_ScheduleId(Pageable pageable, Long scheduleId);

}
