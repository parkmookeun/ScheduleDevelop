package me.parkmookeun.schedule_develop.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionValidException {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> commonExceptionHandler(MethodArgumentNotValidException e) {

        HttpHeaders httpHeaders = new HttpHeaders();

        Map<String, String> map = new HashMap<>();

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", String.valueOf(httpStatus.value()));
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            map.put("message", error.getDefaultMessage());  // 필드와 메시지 추출
        }
        return new ResponseEntity<>(map,httpHeaders, httpStatus);
    }

    @ExceptionHandler(value = NoAuthorizationException.class)
    public ResponseEntity<Map<String,String>> authorizationExceptionHandler(NoAuthorizationException e) {

        HttpHeaders httpHeaders = new HttpHeaders();

        Map<String, String> map = new HashMap<>();

        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", String.valueOf(httpStatus.value()));
        map.put("message", e.getMessage());
        return new ResponseEntity<>(map,httpHeaders, httpStatus);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Map<String,String>> notFoundExceptionHandler(NotFoundException e) {

        HttpHeaders httpHeaders = new HttpHeaders();

        Map<String, String> map = new HashMap<>();

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", String.valueOf(httpStatus.value()));
        map.put("message", e.getMessage());
        return new ResponseEntity<>(map,httpHeaders, httpStatus);
    }

    @ExceptionHandler(value = WrongInputException.class)
    public ResponseEntity<Map<String,String>> wrongInputExceptionHandler(WrongInputException e) {

        HttpHeaders httpHeaders = new HttpHeaders();

        Map<String, String> map = new HashMap<>();

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", String.valueOf(httpStatus.value()));
        map.put("message", e.getMessage());
        return new ResponseEntity<>(map,httpHeaders, httpStatus);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> emailDuplExceptionHandler(SQLIntegrityConstraintViolationException e) {

        HttpHeaders httpHeaders = new HttpHeaders();

        Map<String, String> map = new HashMap<>();

        HttpStatus httpStatus = HttpStatus.CONFLICT;

        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", String.valueOf(httpStatus.value()));
        map.put("message", "SQL에러: 이메일 중복");
        return new ResponseEntity<>(map,httpHeaders, httpStatus);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> logOutExceptionHandler(IllegalArgumentException e) {

        HttpHeaders httpHeaders = new HttpHeaders();

        Map<String, String> map = new HashMap<>();

        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", String.valueOf(httpStatus.value()));
        map.put("message", "로그인 세션이 만료되었습니다!");
        return new ResponseEntity<>(map,httpHeaders, httpStatus);
    }
}
