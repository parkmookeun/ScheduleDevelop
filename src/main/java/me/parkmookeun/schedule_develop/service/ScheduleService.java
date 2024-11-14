package me.parkmookeun.schedule_develop.service;

import lombok.RequiredArgsConstructor;
import me.parkmookeun.schedule_develop.dto.ScheduleResponseDto;
import me.parkmookeun.schedule_develop.dto.ScheduleRequestDto;
import me.parkmookeun.schedule_develop.dto.ScheduleUpdateReqDto;
import me.parkmookeun.schedule_develop.dto.ScheduleUpdateResDto;
import me.parkmookeun.schedule_develop.entity.Schedule;
import me.parkmookeun.schedule_develop.entity.User;
import me.parkmookeun.schedule_develop.exception.NoAuthorizationException;
import me.parkmookeun.schedule_develop.repository.ScheduleRepository;
import me.parkmookeun.schedule_develop.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto, Long id) {
        User user = userRepository.findByIdOrElseThrow(id);

        Schedule schedule = new Schedule(dto);
        schedule.setUser(user);

        Schedule savedUser = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedUser.getScheduleId(), savedUser.getTitle(),
                                       savedUser.getContents(), savedUser.getCreatedAt());
    }

    public List<ScheduleResponseDto> readSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAll();

        return scheduleList.stream().map(ScheduleResponseDto::new).toList();
    }

    public ScheduleResponseDto readSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        return new ScheduleResponseDto(schedule.getScheduleId(), schedule.getTitle(),
                schedule.getContents(), schedule.getCreatedAt());
    }

    @Transactional
    public ScheduleUpdateResDto updateSchedule(ScheduleUpdateReqDto dto, Long scheduleId, Long userId) {
        //userId로 유저 조회
        User user = userRepository.findByIdOrElseThrow(userId);
        //scheduleId로 스케줄 조회
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        //서로의 user_id 비교해서 같으면 수정 다르면 예외 처리!!!
        if(!Objects.equals(user.getId(), schedule.getUser().getId())){
            throw new NoAuthorizationException("다른 사람의 일정을 수정할 권한이 없습니다!");
        }

        schedule.setTitle(dto.getTitle());
        schedule.setContents(dto.getContents());

        return new ScheduleUpdateResDto(schedule.getScheduleId(), schedule.getTitle(),
                schedule.getContents(), schedule.getModifiedAt());
    }

    public void deleteSchedule(Long scheduleId, Long loginId) {
        //userId로 유저 조회
        User user = userRepository.findByIdOrElseThrow(loginId);
        //scheduleId로 스케줄 조회
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        //서로의 user_id 비교해서 같으면 수정 다르면 예외 처리!!!
        if(!Objects.equals(user.getId(), schedule.getUser().getId())){
            throw new NoAuthorizationException("다른 사람의 일정을 삭제할 권한이 없습니다!");
        }

        scheduleRepository.delete(schedule);
    }
}
