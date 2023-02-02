package com.example.JPA.controller;

import com.example.JPA.dto.TicketPurchaseRequest;
import com.example.JPA.service.TicketPurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final TicketPurchaseService ticketPurchaseService;

    public PurchaseController(TicketPurchaseService ticketPurchaseService){
        this.ticketPurchaseService = ticketPurchaseService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> createPurchase(@RequestBody TicketPurchaseRequest ticketPurchaseRequest){
        ticketPurchaseService.purchaseTicket(ticketPurchaseRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id){
        ticketPurchaseService.deleteTicketById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
