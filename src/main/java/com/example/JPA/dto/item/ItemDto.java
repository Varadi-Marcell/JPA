package com.example.JPA.dto.item;

import com.example.JPA.model.Item;
import com.example.JPA.model.Ticket;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ItemDto {

    private Long itemId;
    private Long ticketId;
    private String event;
    private LocalDate startDate;
    private int quantity;
    private Double amount;
    public ItemDto(Item item, Ticket ticket) {
        this.itemId = item.getId();
        this.ticketId = item.getTicketId();
        this.event = ticket.getName();
        this.startDate = ticket.getStartDate();
        this.quantity = item.getQuantity();
        this.amount = item.getAmount();
    }

}
