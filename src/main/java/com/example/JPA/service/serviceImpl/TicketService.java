package com.example.JPA.service.serviceImpl;

import com.example.JPA.dto.TicketDto;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.Ticket;

import com.example.JPA.repository.CardPassRepository;
import com.example.JPA.repository.TicketRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    private final CardPassRepository cardPassRepository;

    public TicketService(TicketRepository ticketRepository, CardPassRepository cardPassRepository) {
        this.ticketRepository = ticketRepository;
        this.cardPassRepository = cardPassRepository;
    }

    public List<TicketDto> getAllTickets() {

        return ticketRepository.findAll().stream()
                .map(s -> s.ticketToTicketDto(s))
                .collect(Collectors.toList());
    }

    public void createTicket(Ticket ticket) {

        LocalDate startDate = ticket.getStartDate();
        LocalDate endDate = ticket.getEndDate();

        if (startDate.isAfter(endDate)){
            throw new RuntimeException("A kezdési idő nem lehet későbbi időpontban ");
        }

        ticketRepository.save(ticket);
    }

    public Ticket getTicketById(Long id) {
        return Optional.of(ticketRepository.findById(id))
                .get()
                .orElseThrow(() ->new ResourceNotFoundException("Ticket with id:" + id + " not found!"));
    }


}
