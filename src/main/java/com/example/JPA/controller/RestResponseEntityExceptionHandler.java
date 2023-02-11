package com.example.JPA.controller;

import com.example.JPA.exceptions.EmailAlreadyExistsException;
import com.example.JPA.exceptions.NotEnoughFundsException;
import com.example.JPA.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleNoSuchElement(ResourceNotFoundException ex, WebRequest request){
        return handleExceptionInternal(ex,ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND,request);
    }

    @ExceptionHandler(value = {NotEnoughFundsException.class})
    protected ResponseEntity<Object> handleNotEnoughFunds(NotEnoughFundsException ex,WebRequest request){
        return handleExceptionInternal(ex,ex.getMessage(),new HttpHeaders(),HttpStatus.PAYMENT_REQUIRED,request);
    }

    @ExceptionHandler(value = {EmailAlreadyExistsException.class})
    protected ResponseEntity<Object> handleEmailAlreadyExists(EmailAlreadyExistsException ex,WebRequest request){
        return handleExceptionInternal(ex,ex.getMessage(),new HttpHeaders(),HttpStatus.CONFLICT,request);
    }
}
