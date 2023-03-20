package com.example.JPA.handshakeHandlers;

import java.security.Principal;

public class PrincipalUser implements Principal {
    private final String id;

    public PrincipalUser(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return this.id;
    }
}
