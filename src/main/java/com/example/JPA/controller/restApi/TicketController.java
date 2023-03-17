package com.example.JPA.controller.restApi;

import com.example.JPA.dto.TicketDto;
import com.example.JPA.dto.TicketDtoResponse;
import com.example.JPA.model.Ticket;
import com.example.JPA.service.serviceImpl.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public TicketDtoResponse getAllTicket(@RequestParam int page, @RequestParam int size){
        return ticketService.getAllTickets(page,size);
    }
    @GetMapping("{id}")
    public Ticket getTicketById(@PathVariable("id") Long id){
        return ticketService.getTicketById(id);
    }
    @PostMapping
    public void createTicket(@RequestBody Ticket ticket){
        ticketService.createTicket(ticket);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTicketById(@PathVariable("id") Long id){
        ticketService.deleteTicketById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
