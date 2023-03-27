package com.example.JPA.service.serviceImpl;

import com.example.JPA.dto.ticket.FilterTicketDto;
import com.example.JPA.dto.ticket.TicketDto;
import com.example.JPA.dto.ticket.TicketDtoResponse;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.Cart;
import com.example.JPA.model.Item;
import com.example.JPA.model.Ticket;

import com.example.JPA.repository.CartRepository;
import com.example.JPA.repository.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final CartRepository cartRepository;

    public TicketService(TicketRepository ticketRepository, CartRepository cartRepository) {
        this.ticketRepository = ticketRepository;
        this.cartRepository = cartRepository;
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public TicketDtoResponse getAllTickets(Map<String, Integer> pagination) {
        int page = Integer.parseInt(String.valueOf(pagination.get("page")));
        int size = Integer.parseInt(String.valueOf(pagination.get("size")));

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Ticket> ticketPage = ticketRepository.findAll(pageRequest);

        return new TicketDtoResponse(ticketPage.getContent()
                .stream()
                .map(s -> s.ticketToTicketDto(s))
                .toList(),
                (int) ticketPage.getTotalElements()
        );
    }

    public void createTicket(Ticket ticket) {

        LocalDate startDate = ticket.getStartDate();
        LocalDate endDate = ticket.getEndDate();

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("A kezdési idő nem lehet későbbi időpontban ");
        }

        ticketRepository.save(ticket);
    }

    public Ticket getTicketById(Long id) {
        return Optional.of(ticketRepository.findById(id))
                .get()
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id:" + id + " not found!"));
    }

    @Transactional
    public void deleteTicketById(Long ticketId) {
        if (ticketRepository.findById(ticketId).isEmpty()) {
            throw new ResourceNotFoundException("Ticket with id:" + ticketId + " not found!");
        }

        List<Cart> cartList = cartRepository.findAll();
        cartList.forEach(cart -> {
            List<Item> itemsToRemove = cart.getItemStream()
                    .filter(item -> item.getTicketId().equals(ticketId))
                    .toList();
            cart.getItemList().removeIf(item -> item.getTicketId().equals(ticketId));
            cartRepository.save(cart);
        });

        ticketRepository.deleteById(ticketId);
    }

    public TicketDtoResponse getTicketByPrice(FilterTicketDto filterTicketDto) {

        int page = filterTicketDto.getPage();
        int size = filterTicketDto.getSize();

        int minPrice = filterTicketDto.getMinPrice();
        int maxPrice = filterTicketDto.getMaxPrice();

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Ticket> ticketPage = ticketRepository.findTicketByPrice(minPrice, maxPrice, pageRequest);

        System.out.println(ticketPage);
        return new TicketDtoResponse(ticketPage.getContent()
                .stream()
                .map(s -> s.ticketToTicketDto(s))
                .toList(),
                (int) ticketPage.getTotalElements()
        );
    }

}
