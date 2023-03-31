package com.example.JPA.controller;

import com.example.JPA.Config;
import com.example.JPA.config.JwtService;
import com.example.JPA.dto.CartDto;
import com.example.JPA.model.*;
import com.example.JPA.repository.CartRepository;
import com.example.JPA.repository.ItemRepository;
import com.example.JPA.repository.TicketRepository;
import com.example.JPA.repository.UserRepository;
import com.example.JPA.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Config.class)
public class CartControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private  JwtService jwtService;

    @Autowired
    private TicketRepository ticketRepository;
    private  Ticket ticket;
    private  User user;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    public void init() {
        user = new User("John Doe", "john.doe@mail.com", "encodedPassword", 30, Role.USER);
        userRepository.save(user);

        ticket = new Ticket("Concert Ticket", "New York City",
                LocalDate.of(2023, 4, 15),
                LocalDate.of(2023, 4, 16), 100,
                "Rock");

        ticketRepository.save(ticket);

        Cart cart = new Cart();
        cartRepository.save(cart);
        user.getCardPass().setCart(cart);
        userRepository.save(user);

        Item item = new Item(ticket.getId(), cart, 3, 432.0);

        itemRepository.save(item);

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItemList(itemList);
        cartRepository.save(cart);
    }

    @Test
    public void testViewCart() {
        String url = "http://localhost:" + port + "/api/v1/cart/view-cart";

        String token = jwtService.generateToken(userRepository.findById(user.getId()).get());
        restTemplate = restTemplate.withBasicAuth(user.getUsername(), user.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<CartDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, CartDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

    }
}

