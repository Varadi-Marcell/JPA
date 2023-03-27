package com.example.JPA.model;

import com.example.JPA.dto.user.UserPersonalDetailsDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "Orders")
@Table(name = "Orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String event;
    private int quantity;
    private double amount;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    private CardPass cardPass;
    public Orders(UserPersonalDetailsDto dto, Ticket ticket, Item item) {
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.event = ticket.getName();
        this.quantity = item.getQuantity();
        this.amount = item.getAmount();
        this.startDate = ticket.getStartDate();
        this.endDate = ticket.getEndDate();
    }
}
