package com.example.JPA.service;


import com.example.JPA.dto.CreateItemRequest;
import com.example.JPA.model.Item;
import com.example.JPA.model.Ticket;

import java.util.List;

public interface ItemService {

    public void addItemToCart(CreateItemRequest request);
    public void removeItemFromCart(Long id);
    public List<Item> getAllItem();

    public Item getShoppingCartItemById(Long id);
    public void updateShoppingCartItemQuantity(Long id, int quantity);
    public Double calculateAmount(Ticket ticket,int quantity);
}
