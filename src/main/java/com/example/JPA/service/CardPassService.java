package com.example.JPA.service;

import com.example.JPA.dto.FestivalCardpassDto;
import com.example.JPA.model.CardPass;
import com.example.JPA.repository.CardPassRepository;
import com.example.JPA.dto.FestivalCardPassAddDto;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.User;
import com.example.JPA.repository.TicketRepository;
import com.example.JPA.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardPassService {

    private final EntityManager em;
    private final CardPassRepository cardPassRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final Logger logger = LoggerFactory.getLogger(CardPassService.class);

    public CardPassService(CardPassRepository cardPassRepository,
                           UserRepository userRepository,
                           TicketRepository ticketRepository,
                           EntityManager em) {
        this.cardPassRepository = cardPassRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.em = em;
    }

    public List<FestivalCardpassDto> getAllFestivalCardPass() {
        return cardPassRepository.findAll()
                .stream()
                .map(s -> s.convertToDTO(s))
                .collect(Collectors.toList());
    }

    public void create(CardPass cardPass) {
        cardPassRepository.save(cardPass);
    }

    @Transactional
    public void createFestivalCardPass(FestivalCardPassAddDto request) {
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
