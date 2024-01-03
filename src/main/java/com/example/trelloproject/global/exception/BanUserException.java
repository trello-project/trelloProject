package com.example.trelloproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class BanUserException extends CustomHandleException {
    public BanUserException(String message){
        super(message);
    }
}

// 상위 핸들러에서 ExceptionHandler...