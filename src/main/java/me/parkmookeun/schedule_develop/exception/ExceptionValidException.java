package me.parkmookeun.schedule_develop.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * 공통예외를 처리하는 클래스
 */
@RestControllerAdvice
public class ExceptionValidException {

    /**
     * 유효성검사 실패시 캐치
     * @param e 유효성검사예외
     * @return 에러응답Map
     */
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

    /**
     * 권한없이 요청시 캐치
     * @param e 권한이없을시 예외
     * @return 에러응답Map
     */
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

    /**
     * 찾는 값이 없을 때 발생하는 예외 캐치
     * @param e 찾는 값이 없을 때 예외
     * @return 에러응답Map
     */
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

    /**
     * 잘못된 값이 들어오면 발생하는 예외 캐치
     * @param e 잘못된 값이 들어왔을 때 예외
     * @return 에러응답Map
     */
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

    /**
     * SQL요청 실패시 발생하는 예외 캐치
     * @param e SQL요청 실패시 발생 예외
     * @return 에러응답Map
     */
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> SQLExceptionHandler(SQLIntegrityConstraintViolationException e) {

        HttpHeaders httpHeaders = new HttpHeaders();

        Map<String, String> map = new HashMap<>();

        HttpStatus httpStatus = HttpStatus.CONFLICT;

        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", String.valueOf(httpStatus.value()));
        map.put("message", e.getMessage());
        return new ResponseEntity<>(map,httpHeaders, httpStatus);
    }

    /**
     * 잘못된 인자 예외 발생시 예외 캐치
     * @param e 잘못된 인자 예외
     * @return 에러응답Map
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> logOutExceptionHandler(IllegalArgumentException e) {

        HttpHeaders httpHeaders = new HttpHeaders();

        Map<String, String> map = new HashMap<>();

        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", String.valueOf(httpStatus.value()));
        map.put("message", e.getMessage());
        return new ResponseEntity<>(map,httpHeaders, httpStatus);
    }

    /**
     * 세션이 없을 때 발생하는 예외 캐치
     * @param e 세션이 없을 시 예외
     * @return 에러응답Map
     */
    @ExceptionHandler(value = ServletRequestBindingException.class)
    public ResponseEntity<Map<String,String>> logOutExceptionHandler(ServletRequestBindingException e) {

        HttpHeaders httpHeaders = new HttpHeaders();

        Map<String, String> map = new HashMap<>();

        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", String.valueOf(httpStatus.value()));
        map.put("message", e.getMessage());
        return new ResponseEntity<>(map,httpHeaders, httpStatus);
    }


//    @ExceptionHandler(value = LoginEssentialException.class)
//    public ResponseEntity<Map<String,String>> loginExceptionHandler(LoginEssentialException e) {
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//
//        Map<String, String> map = new HashMap<>();
//
//        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
//
//        map.put("error type", httpStatus.getReasonPhrase());
//        map.put("code", String.valueOf(httpStatus.value()));
//        map.put("message", e.getMessage());
//        return new ResponseEntity<>(map,httpHeaders, httpStatus);
//    }
}
