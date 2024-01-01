package com.example.trelloproject.global.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommonResponseDto<T> {
    private String message;
    private T data;
    private Integer statusCode;

    public CommonResponseDto(String message, Integer statusCode){
        this.message = message;
        this.statusCode = statusCode;
    }
}
