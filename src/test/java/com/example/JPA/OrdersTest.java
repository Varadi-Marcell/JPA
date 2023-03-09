package com.example.JPA;

import com.example.JPA.model.*;
import com.example.JPA.repository.*;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
public class OrdersTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    CardPassRepository cardPassRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    Faker faker;


    @Test
    void testPurchase(){

        String rawPassword = encoder.encode("asdasd");

        User user = new User("marcibazsi","marcibazsi@mail.com",rawPassword,22, Role.ADMIN);

        userRepository.save(user);

//         = new CardPass(567,user);

        CardPass cardPass = userRepository.findById(user.getId()).get().getCardPass();

        user.setCardPass(cardPass);
        user.getCardPass().setAmount(1000);

        userRepository.save(user);
        System.out.println(userRepository.findById(user.getId()).get().toString());

        //második

        User user2 = new User("marcibazsi2","marcibazsi2@mail.com",rawPassword,22, Role.ADMIN);
        userRepository.save(user2);
//        CardPass cardPass2 = new CardPass(567,user2);
        CardPass cardPass2 = userRepository.findById(user2.getId()).get().getCardPass();

        user2.setCardPass(cardPass2);

        userRepository.save(user2);

        //ticket
        Ticket ticket = getTicket().get(1);
        ticketRepository.save(ticket);

        Ticket ticket2 = getTicket().get(2);
        ticketRepository.save(ticket2);

        //Cart 1 beállítása a cardPass
//        Cart cart = new Cart(cardPass);
//        cartRepository.save(cart);
//        cardPass.setCart(cart);
        Cart cart = cardPass.getCart();

        //Cart 2 beállítása a cardPass
//        Cart cart2 = new Cart(cardPass2);
//        cartRepository.save(cart2);
//        cardPass2.setCart(cart2);

        Cart cart2 = cardPass2.getCart();

        Item item = new Item(ticket.getId(),2,12.0);
        cart.addCartItem(item);
        cartRepository.save(cart);
        Item item1 = itemRepository.findById(1L).get();

        cart.removeItemFromCart(item1);
//        itemRepository.delete(item1);
        cartRepository.save(cart);

        System.out.println(cartRepository.findAll());

//        Item item = new Item(ticket.getId(), 2,12.0);
//        cart.addCartItem(item);
//        cartRepository.save(cart);
//        Item item1 = itemRepository.findById(1L).get();
//
//
//        cart.removeItemFromCart(item1);
//        itemRepository.delete(item1);
//        cartRepository.save(cart);
//
//        System.out.println(cartRepository.findAll());

//
////
//        Item item2 = new Item(ticket.getId(),2,12.0);
////        ticket.setItem(item2);
////        ticket.addItem(item2);
//
//        //keell
//        cart.addCartItem(item2);
//        cart2.addCartItem(item2);
//        itemRepository.save(item2);
//
//
//
//        Item item3 = new Item(ticket2.getId(),cart2,2,12.0);
////        Item item3 = new Item(ticket2,2,12.0);
//        itemRepository.save(item3);
////        ticket.setItem(item3);
//
//        cart.addCartItem(item3);
//
//        cart.removeItemFromCart(item2);
//        cartRepository.save(cart);
//        itemRepository.deleteById(item2.getId());


    }


    public List<Ticket> getTicket(){
        return IntStream
                .rangeClosed(1, 50)
                .mapToObj(s -> {
                    String[] cities = {"Budapest", "Debrecen", "Miskolc"};
                    String[] musicGenres = {"Blues", "Jazz", "Rock", "Pop"};

                    int randomCityIndex = new Random().nextInt(cities.length);
                    int randomMusicIndex = new Random().nextInt(musicGenres.length);

                    return new Ticket(
                            faker.app().name(),
                            cities[randomCityIndex],
                            LocalDate.of(2020, Month.JANUARY, 8),
                            LocalDate.of(2020, Month.JANUARY, 8),
                            faker.number().numberBetween(100, 200),
                            musicGenres[randomMusicIndex]
                    );
                })
                .toList();
    }
}

