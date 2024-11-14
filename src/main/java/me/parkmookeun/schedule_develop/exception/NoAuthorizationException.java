package me.parkmookeun.schedule_develop.exception;

public class NoAuthorizationException extends RuntimeException {
    public NoAuthorizationException(String message) {
        super(message);
    }
}
