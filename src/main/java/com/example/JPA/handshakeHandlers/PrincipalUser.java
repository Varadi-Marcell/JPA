package com.example.JPA.handshakeHandlers;

import com.example.JPA.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;

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
