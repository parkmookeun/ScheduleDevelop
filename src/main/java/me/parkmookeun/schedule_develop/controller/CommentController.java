package me.parkmookeun.schedule_develop.controller;

import lombok.RequiredArgsConstructor;
import me.parkmookeun.schedule_develop.dto.*;
import me.parkmookeun.schedule_develop.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules/{scheduleId}/comments")
public class CommentController {
    private final CommentService commentService;


    @PostMapping
    public ResponseEntity<CommentCreateResDto> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentCreateReqDto dto,
            @SessionAttribute("loginId") Long loginId
    ){
        CommentCreateResDto resDto = commentService.createComment(dto, scheduleId, loginId);

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    @GetMapping("/{commentsId}")
    public ResponseEntity<CommentResponseDto> readComment(@PathVariable Long commentsId,
                                                          @PathVariable Long scheduleId){
        CommentResponseDto dto = commentService.readComment(commentsId,scheduleId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> readAllComments(@PathVariable Long scheduleId,
                                                                    @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                    @RequestParam(required = false, defaultValue = "10") int pageSize){
        List<CommentResponseDto> dtoList = commentService.readAllComments(scheduleId, pageNumber, pageSize);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PutMapping("/{commentsId}")
    public ResponseEntity<CommentUpdateResDto> updateComment(@PathVariable Long commentsId,
                                                             @RequestBody CommentUpdateReqDto dto,
                                                             @SessionAttribute("loginId") Long loginId){

       CommentUpdateResDto resDto = commentService.updateComment(dto,commentsId, loginId);

       return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    @DeleteMapping("/{commentsId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentsId,
                                              @SessionAttribute("loginId") Long loginId){

        commentService.deleteComment(commentsId, loginId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
