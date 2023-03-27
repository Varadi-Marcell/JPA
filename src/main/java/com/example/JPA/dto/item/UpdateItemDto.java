package com.example.JPA.dto.item;

import lombok.Data;

@Data
public class UpdateItemDto {
    private Long itemId;
    private int quantity;
}
