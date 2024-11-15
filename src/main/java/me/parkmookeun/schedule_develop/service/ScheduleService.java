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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 일정을 생성한다.
     * @param dto 일정생성요청DTO
     * @param id 로그인된 유저아이디
     * @return 일정생성응답DTO
     */
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto, Long id) {
        User user = userRepository.findByIdOrElseThrow(id);

        Schedule schedule = new Schedule(dto);
        schedule.setUser(user);

        Schedule savedUser = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedUser.getId(), savedUser.getTitle(),
                                       savedUser.getContents(), savedUser.getCreatedAt());
    }

    /**
     * 페이지번호와 페이지크기에 맞는 일정목록 조회
     * @param pageNumber 페이지번호
     * @param pageSize 페이지크기
     * @return 일정목록DTO
     */
    public List<ScheduleResponseDto> readSchedules(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(Sort.Direction.DESC,"modifiedAt"));
        Page<Schedule> pageSchedules = scheduleRepository.findAll(pageable);

        return pageSchedules.getContent().stream().map(ScheduleResponseDto::new).toList();
    }

    /**
     * 일정아이디에 맞는 일정 조회
     * @param scheduleId 일정아이디
     * @return 일정DTO
     */
    public ScheduleResponseDto readSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(),
                schedule.getContents(), schedule.getCreatedAt());
    }

    /**
     * 로그인된 유저가 아닌 다른 사람의 일정 수정시 권한예외발생, 맞으면 수정
     * @param dto 일정수정요청DTO
     * @param scheduleId 일정아이디
     * @param loginId 로그인된 유저아이디
     * @return 일정수정응답DTO
     */
    @Transactional
    public ScheduleUpdateResDto updateSchedule(ScheduleUpdateReqDto dto, Long scheduleId, Long loginId) {
        //scheduleId로 스케줄 조회
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        //서로의 user_id 비교해서 같으면 수정 다르면 예외 처리!!!
        if(!Objects.equals(schedule.getUser().getId(),loginId)){
            throw new NoAuthorizationException("다른 사람의 일정을 수정할 권한이 없습니다!");
        }

        schedule.setTitle(dto.getTitle());
        schedule.setContents(dto.getContents());

        scheduleRepository.saveAndFlush(schedule);

        return new ScheduleUpdateResDto(schedule.getId(), schedule.getTitle(),
                schedule.getContents(), schedule.getModifiedAt());
    }

    /**
     * 로그인된 유저가 아닌 다른 사람의 일정 삭제시 권한예외발생, 맞으면 삭제
     * @param scheduleId 일정아이디
     * @param loginId 로그인된 유저아이디
     */
    public void deleteSchedule(Long scheduleId, Long loginId) {
        //scheduleId로 스케줄 조회
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        //서로의 user_id 비교해서 같으면 수정 다르면 예외 처리!!!
        if(!Objects.equals(schedule.getUser().getId(), loginId)){
            throw new NoAuthorizationException("다른 사람의 일정을 삭제할 권한이 없습니다!");
        }

        scheduleRepository.delete(schedule);
    }
}
