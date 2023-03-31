package com.example.JPA.controller;

import com.example.JPA.exceptions.EmailAlreadyExistsException;
import com.example.JPA.exceptions.NotEnoughFundsException;
import com.example.JPA.exceptions.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("status", HttpStatus.BAD_REQUEST.value());
//
//        List<String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .collect(Collectors.toList());
//
//        body.put("errors", errors);
//        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//    }

}
