package com.example.JPA.dto;

import com.example.JPA.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FestivalCardpassDto {

    private Long id;
    private String cardHolderName;
    private Integer amount;
//    private List<Order> orderSet;
//    private List<Ticket> ticketList = new ArrayList<>();

}
