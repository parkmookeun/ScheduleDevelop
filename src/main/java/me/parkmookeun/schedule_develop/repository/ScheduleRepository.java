package me.parkmookeun.schedule_develop.repository;

import me.parkmookeun.schedule_develop.entity.Schedule;
import me.parkmookeun.schedule_develop.exception.ScheduleNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()-> new ScheduleNotFoundException(id+"에 해당한 일정이 없습니다!"));
    }
    Page<Schedule> findAll(Pageable pageable);
}
