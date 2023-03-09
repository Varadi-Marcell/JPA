package com.example.JPA.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Orders")
@Table(name = "Orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

   private String customerName;

   private Double amount;

   @ManyToOne
   private CardPass cardPass;



}
