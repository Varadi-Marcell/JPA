package com.example.JPA.service;

import com.example.JPA.dto.CardPassAddDto;
import com.example.JPA.dto.CardpassDto;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.CardPass;
import com.example.JPA.model.User;
import com.example.JPA.repository.CardPassRepository;
import com.example.JPA.repository.TicketRepository;
import com.example.JPA.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardPassService {

    private final CardPassRepository cardPassRepository;
    private final UserRepository userRepository;

    public CardPassService(CardPassRepository cardPassRepository,
                           UserRepository userRepository
                           ) {
        this.cardPassRepository = cardPassRepository;
        this.userRepository = userRepository;
    }

    public List<CardpassDto> getAllFestivalCardPass() {
        return cardPassRepository.findAll()
                .stream()
                .map(s -> s.convertToDTO(s))
                .collect(Collectors.toList());
    }

    public void create(CardPass cardPass) {
        cardPassRepository.save(cardPass);
    }

    @Transactional
    public void createFestivalCardPass(CardPassAddDto request) {
        if (!userRepository.existsUsersById(request.getId())) {
            throw new ResourceNotFoundException("No such user with this id:" + request.getId());
        }

        Optional<User> user = userRepository.findById(request.getId());
        CardPass cardPass = new CardPass(
                request.getAmount(),
                user.get()
        );

        user.get().setCardPass(cardPass);

        userRepository.save(user.get());
    }
}
