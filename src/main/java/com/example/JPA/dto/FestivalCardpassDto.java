package com.example.JPA.dto;

import com.example.JPA.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FestivalCardpassDto {

    private String cardHolderName;
    private Integer amount;
    private List<Ticket> ticketList = new ArrayList<>();

}
