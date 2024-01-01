package com.example.trelloproject.global.exception;

import com.example.trelloproject.global.dto.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponseDto<?> noSuchElementException(NoSuchElementException e) {
        return new CommonResponseDto<>(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NotFoundUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponseDto<?> notFoundUserException(NoSuchElementException e) {
        return new CommonResponseDto<>(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NotFoundBoardException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponseDto<?> notFoundBoardException(NoSuchElementException e) {
        return new CommonResponseDto<>(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NotFoundColumnException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponseDto<?> notFoundColumnException(NoSuchElementException e) {
        return new CommonResponseDto<>(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NotFoundCardException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponseDto<?> notFoundCardException(NoSuchElementException e) {
        return new CommonResponseDto<>(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NotFoundCommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponseDto<?> notFoundCommentException(NoSuchElementException e) {
        return new CommonResponseDto<>(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NotInvitedUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponseDto<?> notInvitedUserException(NoSuchElementException e) {
        return new CommonResponseDto<>(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponseDto<?> unauthorizedAccessException(NoSuchElementException e) {
        return new CommonResponseDto<>(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }
}
