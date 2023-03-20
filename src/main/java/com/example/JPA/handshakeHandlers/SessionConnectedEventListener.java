package com.example.JPA.handshakeHandlers;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class SessionConnectedEventListener implements ApplicationListener<SessionConnectedEvent> {
    private final SessionManagementService sessionManagementService;

    public SessionConnectedEventListener(SessionManagementService sessionManagementService) {
        this.sessionManagementService = sessionManagementService;
    }

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        sessionManagementService.saveSession(event);
    }

}
