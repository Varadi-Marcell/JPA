package com.example.JPA.dto;

import com.example.JPA.model.Ticket;
import com.example.JPA.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketPurchaseRequest implements Serializable {

    private User user;
    private Ticket ticket;
}
