package com.example.JPA.service;

import com.example.JPA.dto.item.CreateItemRequest;
import com.example.JPA.dto.item.UpdateItemDto;
import com.example.JPA.model.*;
import com.example.JPA.repository.CartRepository;
import com.example.JPA.repository.ItemRepository;
import com.example.JPA.repository.TicketRepository;
import com.example.JPA.repository.UserRepository;
import com.example.JPA.service.serviceImpl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;
@Transactional
@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    CartServiceImpl cartService;
    @Mock
    CartRepository cartRepository;;
    @Mock
    UserRepository repository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    TicketRepository ticketRepository;
    @Mock
    UserService userService;
    User user;
    CardPass cardPass;
    Cart cart;
    CreateItemRequest createItemRequest;
    Item item;
    Ticket myTicket;

    @BeforeEach
    void init(){
        user = new User("John Doe", "john.doe@mail.com", "encodedPassword", 30, Role.USER);
        cardPass = new CardPass();
        user.setCardPass(cardPass);
        cardPass.setUser(user);

        cart = new Cart(1l,4324,cardPass,null);
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1l,1l,cart,4,543.0));
        cart.setItemList(itemList);
        myTicket = new Ticket("Concert Ticket", "New York City", LocalDate.of(2023, 4, 15), LocalDate.of(2023, 4, 16), 100, "Rock");
        createItemRequest = new CreateItemRequest();
        createItemRequest.setTicketId(1l);
        createItemRequest.setQuantity(2);
        createItemRequest.setAmount(420.0);
        item = new Item(1l,cart,2,420.0);
        cardPass.setCart(cart);

    }

    @Test
    public void shouldReturnCart() {

        when(cartRepository.findById(1l)).thenReturn(Optional.ofNullable(cart));
        when(ticketRepository.findById(1l)).thenReturn(Optional.ofNullable(myTicket));

        assertEquals(cart.getAmount(),cartService.getShoppingCartById(1l).get().getAmount());
    }

    @Test
    public void shouldAddItemToCart(){

        when(userService.getUser()).thenReturn(user);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        cartService.addItemToCart(createItemRequest);

        verify(cartRepository, times(1)).save(cart);

    }

    @Test
    public void cartShouldBeEmpty(){
        when(userService.getUser()).thenReturn(user);
        cartService.clearCart();

        verify(cartRepository,times(1)).save(cart);
    }

    @Test
    public void shouldRemoveItemFromCartByItemId(){
        when(userService.getUser()).thenReturn(user);

        when(itemRepository.findById(1l)).thenReturn(Optional.ofNullable(item));
        when(ticketRepository.findById(any())).thenReturn(Optional.ofNullable(myTicket));
        cartService.removeItemFromCartByItemId(1l);

        verify(cartRepository,times(1)).save(cart);
    }

    @Test
    public void shouldupdateItemQuantity(){

        UpdateItemDto updateItemDto = new UpdateItemDto();
        updateItemDto.setItemId(1l);
        updateItemDto.setQuantity(6);

        double beforeUpdate = cart.getAmount();
        when(userService.getUser()).thenReturn(user);
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));
        when(ticketRepository.findById(any())).thenReturn(Optional.ofNullable(myTicket));

        cartService.updateItemQuantity(updateItemDto);

        verify(itemRepository,times(1)).save(item);
        verify(cartRepository,times(1)).save(cart);
        assertEquals(item.getQuantity(),updateItemDto.getQuantity());
        assertNotEquals(cart.getAmount(),beforeUpdate);
    }

}
