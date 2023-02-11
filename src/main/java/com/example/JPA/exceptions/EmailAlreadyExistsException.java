package com.example.JPA.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EmailAlreadyExistsException extends RuntimeException
{
    public EmailAlreadyExistsException(String msg){
        super(msg);
    }
}
