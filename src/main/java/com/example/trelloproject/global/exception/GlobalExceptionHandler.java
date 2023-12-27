package com.example.trelloproject.global.exception;

import com.example.trelloproject.global.dto.CommonResponseDTO;
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
    public CommonResponseDTO<?> noSuchElementException(NoSuchElementException e){
        return new CommonResponseDTO<>(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }
}
