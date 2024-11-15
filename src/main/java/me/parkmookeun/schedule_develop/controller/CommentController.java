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

    /**
     * 댓글내용을 받아 댓글을 생성한다.
     * @param scheduleId 일정아이디
     * @param dto 댓글생성요청DTO
     * @return 댓글생성응답DTO
     */
    @PostMapping
    public ResponseEntity<CommentCreateResDto> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentCreateReqDto dto,
            @SessionAttribute("loginId") Long loginId
    ){
        CommentCreateResDto resDto = commentService.createComment(dto, scheduleId, loginId);

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    /**
     * 댓글을 하나 조회한다.
     * @param commentsId 댓글아이디
     * @param scheduleId 일정아이디
     * @return 댓글응답DTO
     */
    @GetMapping("/{commentsId}")
    public ResponseEntity<CommentResponseDto> readComment(@PathVariable Long commentsId,
                                                          @PathVariable Long scheduleId
                                                          ){
        CommentResponseDto dto = commentService.readComment(commentsId,scheduleId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * 일정에 해당하는 모든 댓글을 보여준다.
     * @param scheduleId 일정아이디
     * @param pageNumber 페이지번호
     * @param pageSize 페이지크기
     * @return 댓글목록응답DTO
     */
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> readAllComments(@PathVariable Long scheduleId,
                                                                    @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                    @RequestParam(required = false, defaultValue = "10") int pageSize){
        List<CommentResponseDto> dtoList = commentService.readAllComments(scheduleId, pageNumber, pageSize);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /**
     * 댓글아이디에 해당하는 댓글 수정
     * @param commentsId 댓글아이디
     * @param dto 댓글수정요청DTO
     * @param loginId 로그인된 유저아이디
     * @return 댓글수정응답DTO
     */
    @PutMapping("/{commentsId}")
    public ResponseEntity<CommentUpdateResDto> updateComment(@PathVariable Long commentsId,
                                                             @RequestBody CommentUpdateReqDto dto,
                                                             @SessionAttribute("loginId") Long loginId){

       CommentUpdateResDto resDto = commentService.updateComment(dto,commentsId, loginId);

       return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    /**
     * 댓글아이디에 해당하는 댓글 삭제
     * @param commentsId 댓글아이디
     * @param loginId 로그인된 유저아이디
     * @return 댓글삭제
     */
    @DeleteMapping("/{commentsId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentsId,
                                              @SessionAttribute("loginId") Long loginId){

        commentService.deleteComment(commentsId, loginId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
