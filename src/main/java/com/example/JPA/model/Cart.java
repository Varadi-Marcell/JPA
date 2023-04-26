package com.example.JPA.model;

import com.example.JPA.dto.CartDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount = 0;

    @OneToOne
    @JsonIgnore
    private CardPass cardPass;

    @JsonIgnore
    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Item> itemList = new ArrayList<>();

    public Cart() {

    }

    public void addCartItem(Item item) {
        if (!this.itemList.contains(item)) {
            this.itemList.add(item);
            item.setCart(this);
        }
        calculateAmount();
    }

    public Cart(CardPass cardPass) {
        this.cardPass = cardPass;
    }

    public void removeItemFromCart(Item item) {
        if (this.itemList.contains(item)) {
            this.itemList.remove(item);
            item.setCart(null);
        }
    }

    public void clearCart() {
        this.itemList.clear();
        this.setAmount(0);
    }

    public Stream<Item> getItemStream() {
        return itemList.stream();
    }

    public void calculateAmount() {
        double totalAmount = 0.0;
        for (Item item : itemList) {
            totalAmount += item.getAmount() * item.getQuantity();
        }
        this.amount = totalAmount;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", amount=" + amount +
                ", shoppingCartItemList=" + itemList +
                '}';
    }

}
