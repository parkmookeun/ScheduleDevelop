package me.parkmookeun.schedule_develop.service;

import lombok.RequiredArgsConstructor;
import me.parkmookeun.schedule_develop.dto.*;
import me.parkmookeun.schedule_develop.entity.Comment;
import me.parkmookeun.schedule_develop.entity.Schedule;
import me.parkmookeun.schedule_develop.entity.User;
import me.parkmookeun.schedule_develop.exception.NoAuthorizationException;
import me.parkmookeun.schedule_develop.exception.WrongInputException;
import me.parkmookeun.schedule_develop.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 댓글을 생성한다.
     * @param dto 댓글생성요청DTO
     * @param scheduleId 일정아이디
     * @return 댓글생성응답DTO
     */
    public CommentCreateResDto createComment(CommentCreateReqDto dto, Long scheduleId, Long loginId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        User user = userRepository.findByIdOrElseThrow(loginId);

        Comment comment = new Comment(dto.getContents());

        comment.setSchedule(schedule);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        return new CommentCreateResDto(savedComment);
    }

    /**
     * 댓글아이디에 해당하는 댓글조회
     * @param commentsId 댓글아이디
     * @param scheduleId 일정아이디
     * @return 댓글응답DTO
     */
    public CommentResponseDto readComment(Long commentsId, Long scheduleId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentsId);

        if(!Objects.equals(comment.getSchedule().getId(), scheduleId)){
            throw new WrongInputException("잘못된 일정 아이디 입니다!");
        }

        return new CommentResponseDto(comment.getId(),comment.getContents(),comment.getCreatedAt());
    }

    /**
     * 일정아이디에 해당하는 모든 댓글 조회
     * @param scheduleId 일정아이디
     * @param pageNumber 페이지번호
     * @param pageSize 페이지크기
     * @return 댓글목록DTO
     */
    public List<CommentResponseDto> readAllComments(Long scheduleId, int pageNumber, int pageSize) {
        String criteria = "modifiedAt";
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(Sort.Direction.DESC, criteria));
        Page<Comment> pageSchedules = commentRepository.findAllBySchedule_Id(pageable, scheduleId);

        return pageSchedules.getContent().stream().map(CommentResponseDto::new).toList();
    }

    /**
     * 로그인된 유저가 아닌 다른 사람의 댓글 수정시 권한예외발생, 맞으면 수정
     * @param dto 댓글수정요청DTO
     * @param commentsId 댓글아이디
     * @param loginId 로그인된 유저아이디
     * @return 댓글수정응답DTO
     */
    @Transactional
    public CommentUpdateResDto updateComment(CommentUpdateReqDto dto, Long commentsId, Long loginId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentsId);

        if(!Objects.equals(comment.getUser().getId(), loginId)){
            throw new NoAuthorizationException("다른 사람의 댓글을 수정할 수 없습니다!");
        }

        comment.setContents(dto.getContents());

        commentRepository.saveAndFlush(comment);

        return new CommentUpdateResDto(comment.getId(),comment.getContents(),comment.getModifiedAt());
    }

    /**
     * 로그인된 유저가 아닌 다른 사람의 댓글 삭제시 권한예외발생, 맞으면 삭제
     * @param commentsId 댓글아이디
     * @param loginId 로그인된 유저아이디
     */
    public void deleteComment(Long commentsId, Long loginId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentsId);

        if(!Objects.equals(comment.getUser().getId(), loginId)){
            throw new NoAuthorizationException("다른 사람의 댓글을 삭제할 수 없습니다!");
        }

        commentRepository.delete(comment);
    }
}
