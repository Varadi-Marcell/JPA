package com.example.JPA.dto;

import lombok.Data;

@Data
public class UpdateItemDto {
    private Long itemId;
    private int quantity;
}
