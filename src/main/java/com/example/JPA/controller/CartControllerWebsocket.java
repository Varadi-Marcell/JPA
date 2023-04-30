package com.example.JPA.controller;

import com.example.JPA.dto.ticket.FilterTicketDto;
import com.example.JPA.dto.ticket.TicketDtoResponse;
import com.example.JPA.queries.TicketSearchDto;
import com.example.JPA.service.CartService;
import com.example.JPA.service.serviceImpl.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@AllArgsConstructor
public class CartControllerWebsocket {
    private final CartService cartService;

    private final TicketService ticketService;
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("view-cart")
    @SendTo("cart-response")
    public ResponseEntity<?> viewCart() {
        return new ResponseEntity<>(cartService.viewCart(), HttpStatus.OK);

    }

    @MessageMapping("/tickets")
    @SendTo("/topic/ticket-response")
    public TicketDtoResponse getAllTicket(@RequestBody Map<String, Integer> pagination, @Header("simpSessionId") String sessionId) {
        return ticketService.getAllTickets(pagination);
    }

    @MessageMapping("/secret")
    public void getAllTicket(@Header("simpSessionId") String sessionId, @RequestBody Map<String, Integer> pagination) {

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        messagingTemplate.convertAndSendToUser(sessionId, "/topic/private", ticketService.getAllTickets(pagination), headerAccessor.getMessageHeaders());
    }


    @MessageMapping("/ticketsAbovePrice")
    public ResponseEntity<Void> getTicketByPrice(
            @RequestBody FilterTicketDto filterTicketDto,
            @Header("simpSessionId") String sessionId
    ) {
        System.out.println(sessionId);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        messagingTemplate.convertAndSendToUser(sessionId,"/topic/private", ticketService.getTicketByPrice(filterTicketDto),
                headerAccessor.getMessageHeaders());

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @MessageMapping("filterTicket")
    public ResponseEntity<Void> filterTickets(
            @RequestBody TicketSearchDto dto,
            @Header("simpSessionId") String sessionId
    ){

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        messagingTemplate.convertAndSendToUser(sessionId,"/topic/private", ticketService.filterTickets(dto),
                headerAccessor.getMessageHeaders());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

