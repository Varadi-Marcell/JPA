package com.example.JPA.service.serviceImpl;

import com.example.JPA.dto.CreateOrderDto;
import com.example.JPA.dto.OrdersDto;
import com.example.JPA.model.Cart;
import com.example.JPA.model.Orders;
import com.example.JPA.model.Ticket;
import com.example.JPA.model.User;
import com.example.JPA.repository.*;
import com.example.JPA.service.OrderService;
import com.example.JPA.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final CardPassRepository cardPassRepository;
    private final CartRepository cartRepository;
    private final TicketRepository ticketRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CardPassRepository cardPassRepository, CartRepository cartRepository, TicketRepository ticketRepository,  UserService userService) {
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.cardPassRepository = cardPassRepository;
        this.cartRepository = cartRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void deleteOrderById(Long id) {

    }

    @Override
    public void createOrder(CreateOrderDto dto) {
        Cart cart = userService.getUser().getCardPass().getCart();

        if (cart.getCardPass().getAmount() < cart.getAmount()) {
            throw new RuntimeException("Not enough money");
        }

        List<Orders> ordersList = processItems(cart, dto);
        cart.getCardPass().setAmount((int) (cart.getCardPass().getAmount() - cart.getAmount()));
        ordersList.forEach(orders -> cart.getCardPass().addOrder(orders));
        cart.clearCart();
        cartRepository.save(cart);
        cardPassRepository.save(cart.getCardPass());
    }

    private List<Orders> processItems(Cart cart, CreateOrderDto dto) {
        return cart.getItemList().stream().map(item -> {
            Ticket ticket = ticketRepository.findById(item.getTicketId()).orElseThrow(() -> new RuntimeException("Ticket not found"));
            return new Orders(dto.getFirstName(), dto.getLastName(), dto.getEmail(), ticket.getName(), item.getQuantity(), item.getAmount(), ticket.getStartDate(), ticket.getEndDate());
        }).collect(Collectors.toList());
    }


    @Override
    public List<Orders> getAllOrders() {
       return userService.getUser().getCardPass().getOrders();
//        return orderRepository.findAll();
    }

    @Override
    public Orders getOrderById(Long id) {
        return null;
    }

    @Override
    public List<OrdersDto> getOrdersByDates(String startDate, String endDate) {
        return new OrdersDto().toOrdersDto(orderRepository.filterDates(LocalDate.parse(startDate), LocalDate.parse(endDate),userService.getUser()));
    }
}
