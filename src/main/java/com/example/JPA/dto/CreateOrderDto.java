package com.example.JPA.dto;

import com.example.JPA.model.Cart;
import lombok.Data;

@Data
public class CreateOrderDto {
    private String firstName;
    private String lastName;
    private String email;


}
