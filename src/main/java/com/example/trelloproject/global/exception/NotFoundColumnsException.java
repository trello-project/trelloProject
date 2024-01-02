package com.example.trelloproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotFoundColumnsException extends RuntimeException{

    public NotFoundColumnsException(String message){
        super(message);
    }
}
