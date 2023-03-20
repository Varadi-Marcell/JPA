package com.example.JPA.controller.restApi;

import com.example.JPA.dto.TicketDtoResponse;
import com.example.JPA.model.Ticket;
import com.example.JPA.service.serviceImpl.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final SimpMessagingTemplate messagingTemplate;

    public TicketController(TicketService ticketService, SimpMessagingTemplate messagingTemplate){
        this.ticketService = ticketService;
        this.messagingTemplate = messagingTemplate;
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

        //Websocket...
        messagingTemplate.convertAndSend("/topic/ticket-response", ticketService.getAllTickets(0,10));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
