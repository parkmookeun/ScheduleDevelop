package me.parkmookeun.schedule_develop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.parkmookeun.schedule_develop.dto.ScheduleRequestDto;
import me.parkmookeun.schedule_develop.dto.ScheduleResponseDto;
import me.parkmookeun.schedule_develop.dto.ScheduleUpdateReqDto;
import me.parkmookeun.schedule_develop.dto.ScheduleUpdateResDto;
import me.parkmookeun.schedule_develop.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * 로그인된 유저아이디로 일정을 생성한다.
     * @param dto 일정생성요청DTO
     * @param id 로그인된 유저아이디
     * @return 일정생성응답DTO
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @Valid @RequestBody ScheduleRequestDto dto,
            @SessionAttribute("loginId") Long id){

        ScheduleResponseDto  scheduleResponseDto = scheduleService.createSchedule(dto, id);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    /**
     * 페이지 번호, 페이지 크기에 맞는 일정들을 반환한다.
     * @param pageNumber 페이지번호
     * @param pageSize 페이지크기
     * @return 일정목록응답DTO
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> readSchedules(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                   @RequestParam(required = false, defaultValue = "10") int pageSize){
        List<ScheduleResponseDto> responseDtoList =  scheduleService.readSchedules(pageNumber, pageSize);

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    /**
     * 일정아이디에 맞는 일정 반환
     * @param scheduleId 일정아이디
     * @return 일정응답DTO
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> readSchedule(@PathVariable Long scheduleId){
        ScheduleResponseDto responseDto = scheduleService.readSchedule(scheduleId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 로그인된 유저아이디로 생성된 일정 수정
     * @param scheduleId 일정아이디
     * @param dto 일정수정요청DTO
     * @param loginId 로그인된 유저아이디
     * @return 일정수정응답DTO
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResDto> updateSchedule(@PathVariable Long scheduleId,
                                                               @Valid @RequestBody ScheduleUpdateReqDto dto,
                                                               @SessionAttribute("loginId") Long loginId){
       ScheduleUpdateResDto responseDto = scheduleService.updateSchedule(dto, scheduleId, loginId);

       return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 로그인된 유저아이디로 생성된 일정 삭제
     * @param scheduleId 일정아이디
     * @param loginId 로그인된 유저아이디
     * @return 일정삭제
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId,
                                               @SessionAttribute("loginId") Long loginId){
        scheduleService.deleteSchedule(scheduleId, loginId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
