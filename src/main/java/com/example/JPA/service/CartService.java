package com.example.JPA.service;

import com.example.JPA.dto.CartDto;
import com.example.JPA.dto.CreateItemRequest;
import com.example.JPA.dto.ItemDto;
import com.example.JPA.dto.UpdateItemDto;
import com.example.JPA.model.Cart;
import com.example.JPA.model.Item;

import java.util.Optional;

public interface CartService {

    public void addItemToCart(CreateItemRequest item);
    public Optional<CartDto> removeItemFromCartByItemId(Long itemId);
    public void clearCart();
    public Optional<CartDto> viewCart();
    public Optional<CartDto> getShoppingCartById(Long id);
    public Optional<CartDto> updateItemQuantity(UpdateItemDto itemDto);

}
