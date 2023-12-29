package com.example.trelloproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotInvitedUserException extends RuntimeException{

    public NotInvitedUserException(String message){
        super(message);
    }
}
