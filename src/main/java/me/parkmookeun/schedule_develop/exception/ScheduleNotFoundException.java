package me.parkmookeun.schedule_develop.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
