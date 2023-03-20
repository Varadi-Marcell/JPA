package com.example.JPA.controller;

import com.example.JPA.dto.TicketDtoResponse;
import com.example.JPA.handshakeHandlers.Message;
import com.example.JPA.handshakeHandlers.PrincipalUser;
import com.example.JPA.handshakeHandlers.ResponseMessage;
import com.example.JPA.service.CartService;
import com.example.JPA.service.UserService;
import com.example.JPA.service.serviceImpl.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CartControllerWebsocket {
    private final CartService cartService;

    private final TicketService ticketService;
    private SimpMessagingTemplate messagingTemplate;
    private UserService userService;


    @MessageMapping("view-cart")
    @SendTo("cart-response")
    public ResponseEntity<?> viewCart(){
        return new ResponseEntity<>(cartService.viewCart(), HttpStatus.OK);

    }

//    @MessageMapping("/tickets")
//    @SendTo("/topic/ticket-response")
//    public TicketDtoResponse getAllTicket(@RequestBody PageRequest pageRequest){
//        int page = pageRequest.getPageNumber();
//        int size = pageRequest.getPageSize();
//
//        return ticketService.getAllTickets(0, 10);
////        messagingTemplate.convertAndSend("/topic/ticket-response", ticketService.getAllTickets(page, size));
//    }

    @MessageMapping("/tickets")
    @SendTo("/topic/ticket-response")
    public TicketDtoResponse getAllTicket(@RequestBody Map<String, Integer> pagination, @Header("simpSessionId") String sessionId) {
        int page = pagination.get("page");
        int size = pagination.get("size");
//        String name = userService.getUser().getName();
//        System.out.println(sessionId);
        PageRequest pageRequest = PageRequest.of(page, size);

//        messagingTemplate.convertAndSendToUser(sessionId,"/queue/secret","SECRET MESSAGE");
        return ticketService.getAllTickets2(pageRequest);
    }
//    Just Works....
//    @MessageMapping("/secret")
//    public void getAllTicket(PrincipalUser user) {
//        Message msg = new Message();
//        msg.setMessageContent("SECRET MESSAGE");
//        System.out.println(user.getId());
//        messagingTemplate.convertAndSendToUser(user.getId(), "/topic/private", msg);
//    }

    @MessageMapping("/secret")
    public void getAllTicket(PrincipalUser user,@RequestBody Map<String, Integer> pagination) {
        int page = pagination.get("page");
        int size = pagination.get("size");
        PageRequest pageRequest = PageRequest.of(page, size);

        messagingTemplate.convertAndSendToUser(user.getId(), "/topic/private", ticketService.getAllTickets2(pageRequest));
    }
    public void deleteTicketById(){
        messagingTemplate.convertAndSend("/topic/ticket-response", ticketService.findAll());
    }



}

