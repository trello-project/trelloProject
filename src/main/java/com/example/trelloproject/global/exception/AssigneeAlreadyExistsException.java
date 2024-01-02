package com.example.trelloproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AssigneeAlreadyExistsException extends RuntimeException{

    public AssigneeAlreadyExistsException(String message){
        super(message);
    }
}
