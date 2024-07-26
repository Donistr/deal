package org.example.deal.controller;

import org.example.deal.dto.ResponseObject;
import org.example.deal.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseObject> handle(BaseException exception) {
        return new ResponseEntity<>(new ResponseObject(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseObject> handle(Throwable exception) {
        return new ResponseEntity<>(new ResponseObject(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
