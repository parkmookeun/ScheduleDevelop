package me.parkmookeun.schedule_develop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.parkmookeun.schedule_develop.dto.*;
import me.parkmookeun.schedule_develop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    ResponseEntity<SignUpResponseDto> signup(@RequestBody SignUpRequestDto dto){

        SignUpResponseDto signUpResponseDto = userService.signUp(dto);

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    ResponseEntity<Void> login(@RequestBody LoginRequestDto dto,
                               HttpServletRequest request)
    {
        Long loginId = userService.login(dto);

        HttpSession session = request.getSession();
        session.setAttribute("loginId", loginId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserResponseDto> findById(@PathVariable Long userId){
        UserResponseDto userResponseDto =  userService.findById(userId);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PutMapping
    ResponseEntity<UserUpdateResDto> updateUser(@RequestBody UserUpdateReqDto dto,
                                                HttpServletRequest request){

        HttpSession session = request.getSession();
        Long loginId = (Long) session.getAttribute("loginId");

//        log.info("{}",loginId);

        UserUpdateResDto userUpdateResDto = userService.updateUser(loginId, dto);
        return new ResponseEntity<>(userUpdateResDto, HttpStatus.OK);
    }


    @DeleteMapping
    ResponseEntity<Void> deleteUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        Long loginId = (Long) session.getAttribute("loginId");

        userService.deleteUser(loginId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request){
        HttpSession session = request.getSession();
        Long loginId = (Long) session.getAttribute("loginId");

        return loginId.toString(); //oginId.toString();
    }
}
