package com.example.weather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.concurrent.CompletionException;

@ControllerAdvice
public class ResponseExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    ResponseEntity<String> globalExceptionHandler(Exception ex) {
        HttpStatus status;

        if (ex instanceof CompletionException && null != ex.getCause()) {
            ex = (Exception) ex.getCause();
        }

        if (ex instanceof  IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        String msg = ex.getMessage();
        return new ResponseEntity<>(msg, status);
    }
}
