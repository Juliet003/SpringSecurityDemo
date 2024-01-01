package com.example.springsecuritydemo1.exception;

import com.example.springsecuritydemo1.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {CustomException.class})
    public ApiResponse<?> handleCustomException(CustomException exception){
        return ApiResponse.builder()
                .message(exception.getMessage())
                .data(exception.getHttpStatus())
                .build();
    }

}
