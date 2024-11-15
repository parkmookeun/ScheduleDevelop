package me.parkmookeun.schedule_develop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.parkmookeun.schedule_develop.dto.*;
import me.parkmookeun.schedule_develop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * 유저를 생성한다(=회원가입)
     * @param dto 유저생성요청DTO
     * @return 유저생성응답DTO
     */
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup( @Valid @RequestBody SignUpRequestDto dto){

        SignUpResponseDto signUpResponseDto = userService.signUp(dto);

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.OK);
    }

    /**
     * 세션에 유저아이디 저장
     * @param dto 로그인요청DTO
     * @param request 세션을 가져올 request
     * @return 로그인완료
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto dto,
                               HttpServletRequest request)
    {
        Long loginId = userService.login(dto);

        HttpSession session = request.getSession();
        session.setAttribute("loginId", loginId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 유저를 조회한다.
     * @param userId 유저아이디
     * @return 유저응답DTO
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long userId){
        UserResponseDto userResponseDto =  userService.findById(userId);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * 로그인한 유저의 정보 수정(타인 정보 수정 권한x)
     * @param dto 유저수정요청DTO
     * @param request 세션을 가져올 request
     * @return 유저수정응답DTO
     * @throws SQLIntegrityConstraintViolationException SQL제약조건예외
     */
    @PutMapping
    public ResponseEntity<UserUpdateResDto> updateUser(@Valid @RequestBody UserUpdateReqDto dto,
                                                HttpServletRequest request) throws SQLIntegrityConstraintViolationException {

        HttpSession session = request.getSession();
        Long loginId = (Long) session.getAttribute("loginId");

//        log.info("{}",loginId);

        UserUpdateResDto userUpdateResDto = userService.updateUser(loginId, dto);
        return new ResponseEntity<>(userUpdateResDto, HttpStatus.OK);
    }

    /**
     * 로그인한 유저의 정보 삭제(타인 정보 삭제 권한x)
     * @param request 세션을 가져올 request
     * @return 유저삭제
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        Long loginId = (Long) session.getAttribute("loginId");

        userService.deleteUser(loginId);

        session.invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 로그인된 유저의 세션을 만료시켜 로그아웃시킨다.
     * @param request 세션을 가져올 request
     * @return 세션 만료
     */
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){
        HttpSession session = request.getSession();

        if(session != null){
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
