package com.example.trelloproject.global.exception.handler;

import com.example.trelloproject.global.dto.CommonResponseDto;
import com.example.trelloproject.global.exception.CustomHandleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomHandleException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponseDto<?> globalCustomHandleException(CustomHandleException e) {
        return new CommonResponseDto<>(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }
}
