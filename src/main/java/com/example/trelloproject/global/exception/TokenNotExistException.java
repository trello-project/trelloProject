package com.example.trelloproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class TokenNotExistException extends RuntimeException {
    public TokenNotExistException(String message){
        super(message);
    }
}
