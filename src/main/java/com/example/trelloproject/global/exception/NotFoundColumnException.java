package com.example.trelloproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundColumnException extends RuntimeException{
    public NotFoundColumnException(String message){
        super(message);
    }
}
