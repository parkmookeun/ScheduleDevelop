package me.parkmookeun.schedule_develop.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("해당 유저가 존재하지 않습니다!");
    }
}
