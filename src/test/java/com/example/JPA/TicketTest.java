package com.example.JPA;

import com.example.JPA.model.Ticket;
import com.example.JPA.repository.TicketRepository;
import com.example.JPA.service.serviceImpl.TicketService;
import com.github.javafaker.Faker;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TicketTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;
    @Autowired
    private TicketRepository ticketRepository;

    @MockBean
    private TicketService ticketService;


    @Test
    public void deleteTicketById() throws Exception {

        Ticket ticket = createTickets().get(1);
        ticketRepository.save(ticket);

        // When
        mockMvc.perform(delete("/api/v1/ticket/{id}", ticket.getId()))
                .andExpect(status().isNoContent());


        // Then
        mockMvc.perform(get("/api/v1/ticket/{id}", ticket.getId()))
                .andExpect(status().isNotFound());


    }

    private List<Ticket> createTickets(){
        List<Ticket> ticketList = IntStream
                .rangeClosed(1, 50)
                .mapToObj(s -> {
                    String[] cities = {"Budapest", "Debrecen", "Miskolc"};
                    String[] musicGenres = {"Blues", "Jazz", "Rock", "Pop"};

                    int randomCityIndex = new Random().nextInt(cities.length);
                    int randomMusicIndex = new Random().nextInt(musicGenres.length);

                    LocalDate start = LocalDate.of(2023, Month.JANUARY, 1);
                    long days = ChronoUnit.DAYS.between(start, LocalDate.now());
                    LocalDate randomDate = start.plusDays(new Random().nextInt((int) days + 1));

                    LocalDate end = LocalDate.of(2023, Month.APRIL, 1);
                    days = ChronoUnit.DAYS.between(start, LocalDate.now());
                    LocalDate randomEndDate = start.plusDays(new Random().nextInt((int) days + 1));

                    return new Ticket(
                            faker.app().name(),
                            cities[randomCityIndex],
                            randomDate,
                            randomEndDate,
                            faker.number().numberBetween(100, 200),
                            musicGenres[randomMusicIndex]
                    );
                })
                .toList();
        return ticketList;
    }
}
