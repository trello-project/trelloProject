package com.example.trelloproject.global.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseDTO<T> {
    private String message;
    private T data;
    private Integer statusCode;

    public CommonResponseDTO(String message, Integer statusCode){
        this.message = message;
        this.statusCode = statusCode;
    }
}
