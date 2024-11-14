package me.parkmookeun.schedule_develop.exception;

public class WrongInputException extends RuntimeException {
  public WrongInputException() {
    super("잘못된 입력값입니다!");
  }
}
