package com.example.JPA;

import com.example.JPA.repository.TicketRepository;
import com.example.JPA.service.serviceImpl.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicketTest {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketService ticketService;

//    @Test
//    public void testTicketCreate(){
//        LocalDate localDateStart = LocalDateTime.of(2017, 1, 14, 10, 34);
//        LocalDate localDateEnd = LocalDateTime.of(2018, 1, 14, 10, 34);
//        ticketService.createTicket(new Ticket(1L,"Miskolci koncert","Miskolc",localDateStart,30,localDateEnd));
//
//        System.out.println(ticketRepository.findById(1L).get());
//    }
}
