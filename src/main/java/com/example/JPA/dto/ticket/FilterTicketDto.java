package com.example.JPA.dto.ticket;

import lombok.Data;

@Data
public class FilterTicketDto {
    private Integer minPrice;
    private Integer maxPrice;
    private Integer page;
    private Integer size;
}
