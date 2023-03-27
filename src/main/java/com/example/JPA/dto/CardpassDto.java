package com.example.JPA.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardpassDto {

    private Long id;
    private String cardHolderName;
    private Integer amount;
//    private List<Order> orderSet;
//    private List<Ticket> ticketList = new ArrayList<>();

}
