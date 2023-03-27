package com.example.JPA.model;

import com.example.JPA.dto.CardpassDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "CardPass")
@Getter
@Setter
public class CardPass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cardHolderName;

    private Integer amount;

    @JsonIgnore
    @OneToOne(
            fetch = FetchType.LAZY)
    @JoinColumn(
            name = "users_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "users_id_fk"
            )
    )
    private User user;

    @OneToOne(
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.EAGER,
            mappedBy = "cardPass"
    )
    private Cart cart;

    @JsonIgnore
    @OneToMany(
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "cardPass",
            fetch = FetchType.EAGER
    )
    private List<Orders> orders = new ArrayList<>();

    public CardPass(Integer amount, User user) {
        this.cardHolderName = user.getName();
        this.amount = amount;
        this.user = user;
    }

    public CardPass() {

    }

    @PrePersist
    private void CardPass() {
        this.cart = new Cart(this);
        this.cart.setCardPass(this);
    }


    public void addOrder(Orders order) {
        if (!this.orders.contains(order)) {
            this.orders.add(order);
            order.setCardPass(this);
        }
    }

    @Override
    public String toString() {
        return "FestivalCardPass{" +
                "id=" + id +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", amount=" + amount +
                ", orders=" + orders +
                ", cart=" + cart +
                '}';
    }


    public CardpassDto convertToDTO(CardPass cardPass) {
        return new CardpassDto(
                cardPass.getId(),
                cardPass.getCardHolderName(),
                cardPass.getAmount()
        );
    }

}
