package com.example.musicmanager.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;

import java.util.Date;

@ControllerAdvice
public class ExceptionResponseConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    private final ResponseEntity<Object> handleAllExceptionHandle(Exception ex, WebRequest request) {
        ExceptionMessage message = new ExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    private final ResponseEntity<Object> handleUsernameNotFound(Exception ex, WebRequest request) {
        ExceptionMessage message = new ExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(message, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionMessage message = new ExceptionMessage(new Date(), "valid fail!!!", ex.getBindingResult().getFieldError().toString());
        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }



}
