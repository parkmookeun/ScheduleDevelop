package me.parkmookeun.schedule_develop.service;

import lombok.RequiredArgsConstructor;
import me.parkmookeun.schedule_develop.dto.*;
import me.parkmookeun.schedule_develop.entity.Comment;
import me.parkmookeun.schedule_develop.entity.Schedule;
import me.parkmookeun.schedule_develop.exception.NoAuthorizationException;
import me.parkmookeun.schedule_develop.exception.WrongInputException;
import me.parkmookeun.schedule_develop.repository.CommentRepository;
import me.parkmookeun.schedule_develop.repository.ScheduleRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentCreateResDto createComment(CommentCreateReqDto dto, Long scheduleId, Long loginId) {
        if(loginId == null){
            throw new IllegalArgumentException("로그인 먼저 하세요!");
        }
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(dto.getContents());
        comment.setSchedule(schedule);

        Comment savedComment = commentRepository.save(comment);

        return new CommentCreateResDto(savedComment);
    }

    public CommentResponseDto readComment(Long commentsId, Long scheduleId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentsId);

        if(!Objects.equals(comment.getSchedule().getScheduleId(), scheduleId)){
            throw new WrongInputException("잘못된 일정 아이디 입니다!");
        }

        return new CommentResponseDto(comment.getId(),comment.getContents(),comment.getCreatedAt());
    }

    public List<CommentResponseDto> readAllComments(Long scheduleId, int pageNumber, int pageSize) {
        String criteria = "modifiedAt";
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(Sort.Direction.DESC, criteria));
        Page<Comment> pageSchedules = commentRepository.findAllBySchedule_ScheduleId(pageable, scheduleId);

        return pageSchedules.getContent().stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public CommentUpdateResDto updateComment(CommentUpdateReqDto dto, Long commentsId, Long loginId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentsId);
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(comment.getId());

        if(!Objects.equals(schedule.getUser().getId(), loginId)){
            throw new NoAuthorizationException("다른 사람의 댓글을 수정할 수 없습니다!");
        }

        comment.setContents(dto.getContents());

        commentRepository.saveAndFlush(comment);

        return new CommentUpdateResDto(comment.getId(),comment.getContents(),comment.getModifiedAt());
    }


    public void deleteComment(Long commentsId, Long loginId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentsId);
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(comment.getId());

        if(!Objects.equals(schedule.getUser().getId(), loginId)){
            throw new NoAuthorizationException("다른 사람의 댓글을 삭제할 수 없습니다!");
        }

        commentRepository.delete(comment);
    }
}
