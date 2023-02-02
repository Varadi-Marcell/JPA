package com.example.JPA.controller;

import com.example.JPA.dto.TicketDto;
import com.example.JPA.model.Ticket;
import com.example.JPA.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
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

//    @DeleteMapping("/{id}")
//    public void deleteTicketById(@PathVariable("id") Long id){
//        ticketService.deleteTicketById(id);
//    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable("id") Long id){
        return ticketService.getTicketById(id);
    }
}
