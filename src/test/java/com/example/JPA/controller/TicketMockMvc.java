package com.example.JPA.controller;

import com.example.JPA.model.Ticket;
import com.example.JPA.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class TicketMockMvc {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TicketRepository ticketRepository;

    @BeforeEach
    void init() {
    }

    @Test
    public void shouldThrowBadRequest() throws Exception {
        this.mockMvc.perform(get("/api/v1/ticket"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnTicketById() throws Exception {
        Ticket ticket = ticketRepository.save(new Ticket("Koncert", "Budapest", LocalDate.of(2023, Month.JANUARY, 1), LocalDate.of(2023, Month.MARCH, 1), 430, "Rock"));
        this.mockMvc.perform(get("/api/v1/ticket/101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(ticket.getId()))
                .andExpect(jsonPath("$.name").value(ticket.getName()))
                .andExpect(jsonPath("$.location").value(ticket.getLocation()));
    }

    @Test
    public void shouldCreateTicket() throws Exception {
        Ticket ticket = new Ticket("Koncert", "Budapest", LocalDate.of(2023, Month.JANUARY, 1), LocalDate.of(2023, Month.MARCH, 1), 430, "Rock");


        this.mockMvc.perform(post("/api/v1/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(ticket)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }
}
