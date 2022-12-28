package com.example.JPA.repository;

import com.example.JPA.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    boolean existsTicketByName(String name);
    boolean existsTicketById(Long id);

}
