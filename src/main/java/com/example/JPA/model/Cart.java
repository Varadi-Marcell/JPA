package com.example.JPA.model;

import com.example.JPA.dto.CartDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
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
//            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Item> itemList = new ArrayList<>();

    public void addCartItem(Item item) {
        if (!this.itemList.contains(item)){
            this.itemList.add(item);
            item.setCart(this);
        }
    }

    public Cart(CardPass cardPass) {
        this.cardPass = cardPass;
    }

    public void removeItemFromCart(Item item) {
        if (this.itemList.contains(item)){
            this.itemList.remove(item);
            item.setCart(null);
        }
    }

    public void clearCart(){
        this.itemList.clear();
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
