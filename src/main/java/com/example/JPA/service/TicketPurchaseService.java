package com.example.JPA.service;

import org.springframework.stereotype.Service;

@Service
public class TicketPurchaseService {

//    private final UserRepository userRepository;
//    private final TicketRepository ticketRepository;
//    private final TicketValidator ticketValidator = new TicketValidator();
////    private final Logger logger = LoggerFactory.getLogger(TicketPurchaseService.class);
//
//    public TicketPurchaseService(UserRepository userRepository, TicketRepository ticketRepository) {
//        this.userRepository = userRepository;
//        this.ticketRepository = ticketRepository;
//    }

//    @Transactional
//    public User ticketPurchase(TicketPurchaseRequest request){
//        User user = userRepository.save(request.getUser());
//
//        Ticket ticket = request.getTicket();
//        ticketValidator.validateTicket(request.getTicket().getName());
//
//        if(ticketRepository.existsTicketByName(ticket.getName())){
//            throw new RuntimeException("Ticket already exists!");
//        }
//
//        ticket = ticketRepository.save(ticket);
//
//        user.setTicket(ticket);
//
//        return userRepository.save(user);
//    }

}
