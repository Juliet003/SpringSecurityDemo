package com.example.springsecuritydemo1.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Builder
public class ApiResponse<T> {
    private String message;
    private String status;
    private HttpStatus httpStatus;
    private T data;



    public ApiResponse(String message,HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus=httpStatus;
    }

    public ApiResponse(String message, String status, HttpStatus httpStatus, T data) {
        this.message = message;
        this.status = status;
        this.httpStatus = httpStatus;
        this.data = data;
    }

    public ApiResponse(String message, String status, HttpStatus httpStatus) {
        this.message = message;
        this.status = status;
        this.httpStatus = httpStatus;
    }

    public ApiResponse(Map<String, String> map) {
    }
}
