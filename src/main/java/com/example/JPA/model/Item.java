package com.example.JPA.model;

import com.example.JPA.dto.CreateItemRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.EAGER)
//    private Ticket ticket;
    private Long ticketId;


    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int quantity;

    private Double amount;



    public Item(Long ticketId, Cart cart, int quantity, Double amount) {
        this.ticketId = ticketId;
        this.cart = cart;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Item(Long ticketId, int quantity, Double amount) {
        this.ticketId = ticketId;
        this.quantity = quantity;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", ticketId=" + ticketId +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }



}
