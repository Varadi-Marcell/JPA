//package com.example.JPA.controller;
//
//import com.example.JPA.service.TicketAndFestivalCardPassService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/ticket-and-user")
//@RequiredArgsConstructor
//public class TicketAndFestivalCardPassController {
//    private final TicketAndFestivalCardPassService ticketAndFestivalCardPassService;
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id){
//        ticketAndFestivalCardPassService.deleteTicketById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
