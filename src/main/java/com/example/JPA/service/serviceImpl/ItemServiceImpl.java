package com.example.JPA.service.serviceImpl;

import com.example.JPA.dto.CreateItemRequest;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.Cart;
import com.example.JPA.model.Item;
import com.example.JPA.model.Ticket;
import com.example.JPA.repository.ItemRepository;
import com.example.JPA.repository.UserRepository;
import com.example.JPA.service.ItemService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addItemToCart(CreateItemRequest req) {
//        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//        Cart cart = userRepository.findByEmail(userEmail).get().getCardPass().getCart();
//
//
//        Item item = req.toEntity();
//
//        cart.addCartItem(item);
//
//        itemRepository.save(item);

    }

    @Override
    public void removeItemFromCart(Long id) {
        Optional<Item> cartItem = itemRepository.findById(id);
        Cart cart = cartItem.get().getCart();
        if (!cartItem.isPresent()) {
            throw new ResourceNotFoundException("Item with this id:" + id + " not found!");
        }
        cart.removeItemFromCart(cartItem.get());
        itemRepository.deleteById(id);
    }

    @Override
    public List<Item> getAllItem() {
        return itemRepository.findAll();
    }

    @Override
    public Item getShoppingCartItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item with this id: " + id + " not found!"));
    }

    @Override
    public void updateShoppingCartItemQuantity(Long id, int quantity) {
        Optional<Item> shoppingCartItem = itemRepository.findById(id);

        if (!shoppingCartItem.isPresent()) {
            throw new ResourceNotFoundException("Item with this id:" + id + " not found!");
        }
        shoppingCartItem.get().setQuantity(quantity);

        itemRepository.save(shoppingCartItem.get());
    }

    @Override
    public Double calculateAmount(Ticket ticket, int quantity) {
        return (double) (ticket.getPrice() * quantity);
    }
}
