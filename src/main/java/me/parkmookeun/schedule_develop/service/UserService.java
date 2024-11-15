package me.parkmookeun.schedule_develop.service;

import lombok.RequiredArgsConstructor;
import me.parkmookeun.schedule_develop.config.PasswordEncoder;
import me.parkmookeun.schedule_develop.dto.*;
import me.parkmookeun.schedule_develop.entity.User;
import me.parkmookeun.schedule_develop.exception.WrongInputException;
import me.parkmookeun.schedule_develop.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    /**
     * 회원가입 서비스
     * @param dto 회원가입DTO
     * @return 회원가입응답DTO
     */
    public SignUpResponseDto signUp(SignUpRequestDto dto){
        User user = new User(dto.getUsername(),dto.getEmail(), encoder.encode(dto.getPassword()));
        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser);
    }

    /**
     * 로그인 서비스
     * @param dto 로그인요청DTO
     * @return 유저 아이디
     */
    public Long login(LoginRequestDto dto) {
        User user = userRepository.findByEmailOrElseThrow(dto.getEmail());

        if(!encoder.matches(dto.getPassword(),user.getPassword())){
            throw new WrongInputException("비밀번호가 일치하지 않습니다!");
        }

        return user.getId();
    }

    /**
     * 유저아이디에 맞는 유저 조회
     * @param userId 유저아이디
     * @return 유저응답DTO
     */
    public UserResponseDto findById(Long userId) {
        User findUser = userRepository.findByIdOrElseThrow(userId);

        return new UserResponseDto(findUser.getId(), findUser.getEmail(), findUser.getUsername());
    }

    /**
     * 로그인된 아이디에 맞지 않는 유저 수정시 예외, 맞으면 수정
     * @param loginId 로그인된 유저아이디
     * @param dto 유저수정요청DTO
     * @return 유저수정응답DTO
     * @throws SQLIntegrityConstraintViolationException SQL예외
     */
    @Transactional
    public UserUpdateResDto updateUser(Long loginId, UserUpdateReqDto dto) throws SQLIntegrityConstraintViolationException {
        User user = userRepository.findByIdOrElseThrow(loginId);

        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());

        return new UserUpdateResDto(user);
    }

    /**
     * 로그인된 아이디에 맞지 않는 유저 삭제시 예외, 맞으면 삭제
     * @param loginId 로그인된 아이디
     */
    public void deleteUser(Long loginId) {
        userRepository.deleteById(loginId);
    }
}
