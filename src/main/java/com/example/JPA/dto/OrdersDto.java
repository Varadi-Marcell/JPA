package com.example.JPA.dto;

import com.example.JPA.model.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String event;
    private int quantity;
    private double amount;
    private LocalDate startDate;
    private LocalDate endDate;
    public List<OrdersDto> toOrdersDto(List<Orders> ordersList){
        return ordersList.stream().map(orders ->new OrdersDtoBuilder()
                .id(orders.getId())
                .firstName(orders.getFirstName())
                .lastName(orders.getLastName())
                .email(orders.getEmail())
                .event(orders.getEvent())
                .quantity(orders.getQuantity())
                .amount(orders.getAmount())
                .startDate(orders.getStartDate())
                .endDate(orders.getEndDate())
                .build()).collect(Collectors.toList());
    }
}
