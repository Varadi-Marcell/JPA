package com.example.JPA.service;

import com.example.JPA.dto.TicketDto;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.Ticket;

import com.example.JPA.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<TicketDto> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(s -> s.ticketToTicketDto(s))
                .collect(Collectors.toList());
    }

    public void createTicket(Ticket ticket) {

        LocalDateTime startDate = ticket.getStartDate();
        LocalDateTime endDate = ticket.getEndDate();

        if (startDate.isAfter(endDate) || startDate.isEqual(endDate)){
            throw new RuntimeException("A kezdési idő nem lehet később vagy a ugyanabban az időpontban ");
        }

        ticketRepository.save(ticket);
    }

    public Ticket getTicketById(Long id) {
        return Optional.of(ticketRepository.findById(id))
                .get()
                .orElseThrow(() ->new ResourceNotFoundException("Ticket with id:" + id + " not found!"));
    }

    public void deleteTicketById(Long id) {
        if (!ticketRepository.existsTicketById(id)) {
            throw new ResourceNotFoundException("Ticket with id:" + id + " not found!");
        }

        ticketRepository.deleteById(id);
    }
}
