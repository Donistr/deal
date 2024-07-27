package org.example.deal.controller;

import org.example.deal.dto.ResponseObject;
import org.example.deal.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Этот класс ловит все исключения в приложении
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Ловит все исключения типа BaseException
     * @param exception исключение
     * @return ответ сервера
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseObject> handle(BaseException exception) {
        return new ResponseEntity<>(new ResponseObject(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Ловит все исключения типа Throwable
     * @param exception исключение
     * @return ответ сервера
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseObject> handle(Throwable exception) {
        return new ResponseEntity<>(new ResponseObject(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
