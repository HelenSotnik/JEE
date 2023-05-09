package com.hillel.demo.core.application.controller;

import com.hillel.demo.core.application.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ApiError> handler(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(new ApiError(LocalDateTime.now(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
