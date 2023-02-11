package com.example.JPA.service;

import com.example.JPA.dto.TicketPurchaseRequest;
import com.example.JPA.exceptions.NotEnoughFundsException;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.FestivalCardPass;
import com.example.JPA.model.Ticket;
import com.example.JPA.model.User;
import com.example.JPA.repository.FestivalCardPassRepository;
import com.example.JPA.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketPurchaseService {

    private final TicketRepository ticketRepository;
    private final FestivalCardPassRepository festivalCardPassRepository;
    private final Logger logger = LoggerFactory.getLogger(TicketPurchaseService.class);

    public TicketPurchaseService(TicketRepository ticketRepository, FestivalCardPassRepository festivalCardPassRepository) {
        this.ticketRepository = ticketRepository;
        this.festivalCardPassRepository = festivalCardPassRepository;
    }

    @Transactional
    public void purchaseTicket(TicketPurchaseRequest request) {

        Optional<Ticket> ticket = ticketRepository.findById(request.ticketId());
        Optional<FestivalCardPass> festivalCardPass = festivalCardPassRepository.findById(request.festivalCardPassId());

        if (!ticket.isPresent()) {
            throw new ResourceNotFoundException(String.format("Ticket with:%d id not exists", ticket.get().getId()));
        }

        if (!festivalCardPass.isPresent()) {
            throw new ResourceNotFoundException(String.format("FestivalCard with:%d id not exists", festivalCardPass.get().getId()));
        }

        validateCardFundsForTicketPurchase(festivalCardPass.get().getAmount(), ticket.get().getPrice());
        festivalCardPass.get().setAmount(deductFundsFromCard(festivalCardPass.get().getAmount(), ticket.get().getPrice()));

        festivalCardPass.get().addTicket(ticket.get());

        festivalCardPassRepository.save(festivalCardPass.get());
    }

    public void validateCardFundsForTicketPurchase(Integer avaiableFunds, Integer ticketPrice) {
        if (!(avaiableFunds > ticketPrice + ticketPrice * 0.27)) {
            throw new NotEnoughFundsException("There is not enough money on the card. " + "Current amount:" + avaiableFunds);
        }
    }

    public Integer deductFundsFromCard(Integer avaiableFunds, Integer ticketPrice) {
        double deductedFunds = avaiableFunds - (ticketPrice + ticketPrice * 0.27);
        return (int) deductedFunds;
    }


}
