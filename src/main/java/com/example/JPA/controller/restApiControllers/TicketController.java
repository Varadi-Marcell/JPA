package com.example.JPA.controller.restApiControllers;

import com.example.JPA.dto.ticket.FilterTicketDto;
import com.example.JPA.dto.ticket.TicketDto;
import com.example.JPA.dto.ticket.TicketDtoResponse;
import com.example.JPA.model.Ticket;
import com.example.JPA.queries.TicketSearchDto;
import com.example.JPA.service.serviceImpl.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final SimpMessagingTemplate messagingTemplate;

    public TicketController(TicketService ticketService, SimpMessagingTemplate messagingTemplate) {
        this.ticketService = ticketService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public TicketDtoResponse getAllTicket(
            @RequestBody Map<String, Integer> pagination
    ) {
        return ticketService.getAllTickets(pagination);
    }


    @GetMapping("{id}")
    public Ticket getTicketById(@PathVariable("id") Long id) {
        return ticketService.getTicketById(id);
    }

    @PostMapping
    public void createTicket(@RequestBody Ticket ticket) {
        ticketService.createTicket(ticket);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTicketById(
            @PathVariable("id") Long id,
            @RequestParam Map<String, Integer> pagination) {

        ticketService.deleteTicketById(id);

        //Websocket...
        messagingTemplate.convertAndSend("/topic/ticket-response", ticketService.getAllTickets(pagination));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("location")
    public ResponseEntity<List<String>> getAllLocations(){
        return new ResponseEntity<>(ticketService.getAllLocation(),HttpStatus.OK);
    }

    @GetMapping("genre")
    public ResponseEntity<List<String>> getAllGenre(){
        return new ResponseEntity<>(ticketService.getAllGenre(),HttpStatus.OK);
    }

}
