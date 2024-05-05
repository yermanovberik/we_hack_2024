package com.example.demo.exception.handler;

import com.example.demo.dto.response.ErrorResponse;
import com.example.demo.enums.ErrorCode;
import com.example.demo.exception.PasswordMismatchException;
import com.example.demo.exception.UserAlreadyExistAuthenticationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    @ExceptionHandler(UserAlreadyExistAuthenticationException.class)
    public ResponseEntity<ErrorResponse> exceptionUserAlreadyExistHandler(UserAlreadyExistAuthenticationException e){
        log.info(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .errorCode(ErrorCode.USER_EXISTS.getStatusCode())
                .build();
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> exceptionPasswordMismatchHandler(PasswordMismatchException e) {
        log.info(e.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .errorCode(ErrorCode.PASSWORD_MISMATCH.getStatusCode())
                .build();
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionUsernameNotFoundHandler(UsernameNotFoundException e) {
        log.info(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .errorCode(ErrorCode.USERNAME_NOT_FOUND.getStatusCode())
                .build();
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionEntityNotFoundHandler(EntityNotFoundException e) {
        log.info(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .errorCode(ErrorCode.ENTITY_NOT_FOUND.getStatusCode())
                .build();
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

}
