package com.example.JPA.utils;

public class TicketValidator {

    public boolean validateTicket(String name){
        if (name == null){
            throw  new RuntimeException("Name must be not null!");
        }
        return true;
    }
}
