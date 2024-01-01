package com.example.springsecuritydemo1.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class CustomException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public CustomException(String message, String message1, HttpStatus httpStatus) {
        super(message);
        this.message = message1;
        this.httpStatus = httpStatus;
    }

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(String s, HttpStatus httpStatus) {
    }
}
