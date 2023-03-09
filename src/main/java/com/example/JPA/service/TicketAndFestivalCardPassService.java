//package com.example.JPA.service;
//
//import com.example.JPA.exceptions.ResourceNotFoundException;
//import com.example.JPA.model.Ticket;
//import com.example.JPA.repository.FestivalCardPassRepository;
//import com.example.JPA.repository.TicketRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class TicketAndFestivalCardPassService {
//    private final TicketRepository ticketRepository;
//
//    private final FestivalCardPassRepository festivalCardPassRepository;
//
//    @Transactional
//    public void deleteTicketById(Long id) {
//        Ticket ticket = ticketRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id:" + id + " not found!"));
//
//        festivalCardPassRepository.findAll()
//                .stream()
//                .filter(s -> s.getTicketList().contains(ticket))
//                .peek(s -> s.removeTicket(ticket))
//                .collect(Collectors.toList());
//
//        ticketRepository.deleteById(id);
//    }
//}
