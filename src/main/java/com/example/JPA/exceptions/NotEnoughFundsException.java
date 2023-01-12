package com.example.JPA.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PAYMENT_REQUIRED)
public class NotEnoughFundsException extends RuntimeException{

    public NotEnoughFundsException(String msg) {
        super(msg);
    }
}
