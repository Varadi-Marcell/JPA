package com.example.JPA.dto.item;

import com.example.JPA.model.Item;
import lombok.Data;

@Data
public class CreateItemRequest {
    private Long ticketId;
    private int quantity;
    private Double amount;

    public Item toEntity(CreateItemRequest item){
        return new Item(
                this.getTicketId(),
                this.getQuantity(),
                this.getAmount()
        );
    }
}
