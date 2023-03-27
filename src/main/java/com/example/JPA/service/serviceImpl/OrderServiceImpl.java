package com.example.JPA.service.serviceImpl;

import com.example.JPA.dto.user.UserPersonalDetailsDto;
import com.example.JPA.dto.OrdersDto;
import com.example.JPA.exceptions.NotEnoughFundsException;
import com.example.JPA.model.Cart;
import com.example.JPA.model.Orders;
import com.example.JPA.model.Ticket;
import com.example.JPA.model.User;
import com.example.JPA.repository.*;
import com.example.JPA.service.OrderService;
import com.example.JPA.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final CardPassRepository cardPassRepository;
    private final CartRepository cartRepository;
    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;
    @Override
    public void deleteOrderById(Long id) {

    }

    @Override
    public void createOrder(UserPersonalDetailsDto dto) {
//        Cart cart = userService.getUser().getCardPass().getCart();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = userRepository.findByEmail(userEmail).get().getCardPass().getCart();


        if (cart.getCardPass().getAmount() < cart.getAmount()) {
            throw new NotEnoughFundsException("You don't have enough money in your balance");
        }

        List<Orders> ordersList = makeOrdersByCartAndPersonalDetails(cart, dto);
        cart.getCardPass().setAmount((int) (cart.getCardPass().getAmount() - cart.getAmount()));
        ordersList.forEach(orders -> cart.getCardPass().addOrder(orders));

        cart.clearCart();

        cartRepository.save(cart);
        cardPassRepository.save(cart.getCardPass());
    }

    private List<Orders> makeOrdersByCartAndPersonalDetails(Cart cart, UserPersonalDetailsDto dto) {
        return cart.getItemList().stream()
                .map(item -> {
                    Ticket ticket = ticketRepository
                            .findById(item.getTicketId()).orElseThrow(() -> new RuntimeException("Ticket not found"));
                    return new Orders(dto, ticket, item);
                }).collect(Collectors.toList());
    }


    @Override
    public List<Orders> getAllOrders() {
        return userService.getUser().getCardPass().getOrders();
    }

    @Override
    public Orders getOrderById(Long id) {
        return null;
    }

    @Override
    public List<OrdersDto> getOrdersByDates(String startDate, String endDate) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail).get();

        return new OrdersDto().toOrdersDto(
                orderRepository.filterDates(
                        LocalDate.parse(startDate),
                        LocalDate.parse(endDate),
                        user
                )
        );
    }
}
