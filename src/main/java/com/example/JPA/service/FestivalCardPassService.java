package com.example.JPA.service;

import com.example.JPA.dto.FestivalCardpassDto;
import com.example.JPA.repository.FestivalCardPassRepository;
import com.example.JPA.dto.FestivalCardPassAddDto;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.FestivalCardPass;
import com.example.JPA.model.User;
import com.example.JPA.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FestivalCardPassService {

    private final FestivalCardPassRepository festivalCardPassRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(FestivalCardPassService.class);

    public FestivalCardPassService(FestivalCardPassRepository festivalCardPassRepository, UserRepository userRepository) {
        this.festivalCardPassRepository = festivalCardPassRepository;
        this.userRepository = userRepository;
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

        logger.info(request.getFestivalCardPass().getCardHolderName());

        FestivalCardPass festivalCardPass = new FestivalCardPass(
                request.getFestivalCardPass().getCardHolderName(),
                request.getFestivalCardPass().getAmount(),
                user.get()
        );

        user.get().setFestivalCardPass(festivalCardPass);

        userRepository.save(user.get());
    }


    @Transactional
    public void addTicket(){

    }
}
