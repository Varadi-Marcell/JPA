package com.example.JPA.controller;

import com.example.JPA.dto.TicketDto;
import com.example.JPA.model.Ticket;
import com.example.JPA.service.serviceImpl.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<TicketDto> getAllTicket(){
        return ticketService.getAllTickets();
    }

    @PostMapping
    public void createTicket(@RequestBody Ticket ticket){
        ticketService.createTicket(ticket);
    }


    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable("id") Long id){
        return ticketService.getTicketById(id);
    }
}
