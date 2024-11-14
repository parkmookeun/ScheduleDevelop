package me.parkmookeun.schedule_develop.service;

import lombok.RequiredArgsConstructor;
import me.parkmookeun.schedule_develop.dto.*;
import me.parkmookeun.schedule_develop.entity.User;
import me.parkmookeun.schedule_develop.exception.UserNotFoundException;
import me.parkmookeun.schedule_develop.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public SignUpResponseDto signUp(SignUpRequestDto dto){
        User user = new User(dto.getUsername(),dto.getEmail(),dto.getPassword());
        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser);
    }

    public Long login(LoginRequestDto dto) {
        User user = userRepository.findByEmailAndPasswordOrElseThrow(dto.getEmail(), dto.getPassword());

        return user.getId();
    }

    public UserResponseDto findById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }
        User findUser = optionalUser.get();
        return new UserResponseDto(findUser.getId(), findUser.getEmail(), findUser.getUsername());
    }

    @Transactional
    public UserUpdateResDto updateUser(Long loginId, UserUpdateReqDto dto) {
        User user = userRepository.findByIdOrElseThrow(loginId);

        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());

        return new UserUpdateResDto(user);
    }

    public void deleteUser(Long loginId) {
        userRepository.deleteById(loginId);
    }
}
