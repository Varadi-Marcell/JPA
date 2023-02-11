package com.example.JPA.service;

import com.example.JPA.dto.TicketDto;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.Ticket;

import com.example.JPA.repository.FestivalCardPassRepository;
import com.example.JPA.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    private final FestivalCardPassRepository festivalCardPassRepository;

    public TicketService(TicketRepository ticketRepository, FestivalCardPassRepository festivalCardPassRepository) {
        this.ticketRepository = ticketRepository;
        this.festivalCardPassRepository = festivalCardPassRepository;
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
