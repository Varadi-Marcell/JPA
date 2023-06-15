package com.example.JPA.auth;

import com.example.JPA.config.JwtService;
import com.example.JPA.exceptions.EmailAlreadyExistsException;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.Role;
import com.example.JPA.model.User;
import com.example.JPA.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.existsUsersByEmail(request.getEmail())){
            System.out.println("asd");
            throw new EmailAlreadyExistsException("The email is already registered!");
        }
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .age(23)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (!repository.existsUsersByEmail(request.getEmail())){
            throw new ResourceNotFoundException("Email does not exists!");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userDto(user.convertToDto(user))
                .build();
    }
}