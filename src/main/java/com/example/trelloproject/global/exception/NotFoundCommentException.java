package com.example.trelloproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundCommentException extends RuntimeException{
    public NotFoundCommentException(String message){
        super(message);
    }
}
