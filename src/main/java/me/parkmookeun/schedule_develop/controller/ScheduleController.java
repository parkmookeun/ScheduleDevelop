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

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @Valid @RequestBody ScheduleRequestDto dto,
            @SessionAttribute("loginId") Long id){

        ScheduleResponseDto  scheduleResponseDto = scheduleService.createSchedule(dto, id);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> readSchedules(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                   @RequestParam(required = false, defaultValue = "10") int pageSize){
        List<ScheduleResponseDto> responseDtoList =  scheduleService.readSchedules(pageNumber, pageSize);

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> readSchedule(@PathVariable Long scheduleId){
        ScheduleResponseDto responseDto = scheduleService.readSchedule(scheduleId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResDto> updateSchedule(@PathVariable Long scheduleId,
                                                               @Valid @RequestBody ScheduleUpdateReqDto dto,
                                                               @SessionAttribute("loginId") Long loginId){
       ScheduleUpdateResDto responseDto = scheduleService.updateSchedule(dto, scheduleId, loginId);

       return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId,
                                               @SessionAttribute("loginId") Long loginId){
        scheduleService.deleteSchedule(scheduleId, loginId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
