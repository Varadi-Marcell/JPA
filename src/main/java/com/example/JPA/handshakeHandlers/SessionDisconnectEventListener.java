package com.example.JPA.handshakeHandlers;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

@Component
public class SessionDisconnectEventListener implements ApplicationListener<AbstractSubProtocolEvent> {

    private SessionManagementService sessionManagementService;

    public SessionDisconnectEventListener(SessionManagementService sessionManagementService) {
        this.sessionManagementService = sessionManagementService;
    }


    @Override
    public void onApplicationEvent(AbstractSubProtocolEvent abstractSubProtocolEvent) {
        sessionManagementService.removeSession(abstractSubProtocolEvent);

    }
}