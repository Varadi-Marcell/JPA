package com.example.JPA.handshakeHandlers;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Service
public class SessionManagementService {
    private final Set<Principal> sessionHashSet = new HashSet<>();

    private final SimpMessagingTemplate simpMessagingTemplate;

    public SessionManagementService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void removeSession(AbstractSubProtocolEvent abstractSubProtocolEvent) {
        this.sessionHashSet.remove(abstractSubProtocolEvent.getUser());
        simpMessagingTemplate.convertAndSend("/topic/users", this.sessionHashSet);
    }

    public void saveSession(SessionConnectedEvent event) {
        this.sessionHashSet.add(event.getUser());
    }
}
