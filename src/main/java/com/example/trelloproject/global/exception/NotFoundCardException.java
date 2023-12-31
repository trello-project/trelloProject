package com.example.trelloproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotFoundCardException extends RuntimeException{

    public NotFoundCardException(String message){
        super(message);
    }
}
