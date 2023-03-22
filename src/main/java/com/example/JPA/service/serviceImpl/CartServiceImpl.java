package com.example.JPA.service.serviceImpl;

import com.example.JPA.dto.CartDto;
import com.example.JPA.dto.CreateItemRequest;
import com.example.JPA.dto.ItemDto;
import com.example.JPA.dto.UpdateItemDto;
import com.example.JPA.model.Cart;
import com.example.JPA.model.Item;
import com.example.JPA.repository.CartRepository;
import com.example.JPA.repository.ItemRepository;
import com.example.JPA.repository.TicketRepository;
import com.example.JPA.repository.UserRepository;
import com.example.JPA.service.CartService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final TicketRepository ticketRepository;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, ItemRepository itemRepository, TicketRepository ticketRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<CartDto> getShoppingCartById(Long id) {
        Cart cart = cartRepository.findById(id).get();

        List<ItemDto> itemDtos = cart.getItemList().stream()
                .map(item -> new ItemDto(item, ticketRepository.findById(item.getTicketId()).get()))
                .toList();

        return Optional.of(new CartDto(cart.getId(), cart.getAmount(), itemDtos));
    }

    @Override
    @Transactional
    public Optional<CartDto> updateItemQuantity(UpdateItemDto itemDto) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = userRepository.findByEmail(userEmail).get().getCardPass().getCart();

        Item item = itemRepository.findById(itemDto.getItemId()).get();
        item.setQuantity(itemDto.getQuantity());
        cart.calculateAmount();

        itemRepository.save(item);
        cartRepository.save(cart);

        List<ItemDto> itemDtos = cart.getItemList().stream()
                .map(item1 -> new ItemDto(item1, ticketRepository.findById(item1.getTicketId()).get()))
                .toList();

        return Optional.of(new CartDto(cart.getId(), cart.getAmount(), itemDtos));

    }

    @Override
    public void addItemToCart(CreateItemRequest req) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = userRepository.findByEmail(userEmail).get().getCardPass().getCart();

        Item item = req.toEntity(req);
        cart.addCartItem(item);
        cartRepository.save(cart);
    }

    @Override
    public Optional<CartDto> removeItemFromCartByItemId(Long itemId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = userRepository.findByEmail(userEmail).get().getCardPass().getCart();
        Item item = itemRepository.findById(itemId).orElseThrow();


        cart.setAmount(cart.getAmount()-(item.getAmount()*item.getQuantity()));
        cart.removeItemFromCart(item);
        cartRepository.save(cart);

        List<ItemDto> itemDtos = cart.getItemList().stream()
                .map(item1 -> new ItemDto(item1, ticketRepository.findById(item1.getTicketId()).get()))
                .toList();

        return Optional.of(new CartDto(cart.getId(), cart.getAmount(), itemDtos));

    }


    @Override
    public void clearCart() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = userRepository.findByEmail(userEmail).get().getCardPass().getCart();

        cart.clearCart();

        cartRepository.save(cart);
    }

    @Override
    public Optional<CartDto> viewCart() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = userRepository.findByEmail(userEmail).get().getCardPass().getCart();

        List<ItemDto> itemDtos = cart.getItemList().stream()
                .map(item -> new ItemDto(item, ticketRepository.findById(item.getTicketId()).get()))
                .toList();

        return Optional.of(new CartDto(cart.getId(), cart.getAmount(), itemDtos));

    }


}
