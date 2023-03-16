package com.example.JPA.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Orders")
@Table(name = "Orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders  {
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

    public Orders(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

    public Orders(String firstName, String lastName, String email, String event, int quantity, double amount,LocalDate startDate,LocalDate endDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.event = event;
        this.quantity = quantity;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
