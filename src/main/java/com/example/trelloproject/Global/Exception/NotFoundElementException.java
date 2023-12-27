package com.example.trelloproject.Global.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotFoundElementException extends RuntimeException{

    public NotFoundElementException(String message){
        super(message);
    }
}