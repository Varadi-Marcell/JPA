package com.example.JPA.service;

import com.example.JPA.dto.FestivalCardpassDto;
import com.example.JPA.repository.FestivalCardPassRepository;
import com.example.JPA.dto.FestivalCardPassAddDto;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.FestivalCardPass;
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
public class FestivalCardPassService {

    private final EntityManager em;
    private final FestivalCardPassRepository festivalCardPassRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final Logger logger = LoggerFactory.getLogger(FestivalCardPassService.class);

    public FestivalCardPassService(FestivalCardPassRepository festivalCardPassRepository,
                                   UserRepository userRepository,
                                   TicketRepository ticketRepository,
                                   EntityManager em) {
        this.festivalCardPassRepository = festivalCardPassRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.em = em;
    }

    public List<FestivalCardpassDto> getAllFestivalCardPass() {
        return festivalCardPassRepository.findAll()
                .stream()
                .map(s -> s.convertToDTO(s))
                .collect(Collectors.toList());
    }

    public void create(FestivalCardPass festivalCardPass) {
        festivalCardPassRepository.save(festivalCardPass);
    }

    @Transactional
    public void createFestivalCardPass(FestivalCardPassAddDto request) {
        if (!userRepository.existsUsersById(request.getId())) {
            throw new ResourceNotFoundException("No such user with this id:" + request.getId());
        }

        Optional<User> user = userRepository.findById(request.getId());

        FestivalCardPass festivalCardPass = new FestivalCardPass(
                request.getFestivalCardPass().getCardHolderName(),
                request.getFestivalCardPass().getAmount(),
                user.get()
        );

        user.get().setFestivalCardPass(festivalCardPass);

        userRepository.save(user.get());
    }

    @Transactional
    public void deleteFestivalCard(Long id){


//        FestivalCardPass festivalCardPass = em.find(FestivalCardPass.class,id);
//        festivalCardPass.getTicketList().forEach(s -> s.setFestivalCardPass(null));
//        ticketRepository.findAll().stream().forEach(s -> logger.info(s.toString()));
//        em.flush();
//        em.remove(festivalCardPass);

        Optional<FestivalCardPass> festivalCardPass=festivalCardPassRepository.findById(id);
        if (!festivalCardPass.isPresent()){
            throw new ResourceNotFoundException("Festival card with this id:"+id+" does not exists");
        }
        festivalCardPass.get().getTicketList().clear();
        festivalCardPassRepository.deleteById(id);
    }
}
