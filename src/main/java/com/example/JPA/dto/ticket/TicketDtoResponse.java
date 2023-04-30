package com.example.JPA.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class TicketDtoResponse {
    private List<TicketDto> ticketDtoList;
    private int size;
}
