package com.example.JPA.repository;

import com.example.JPA.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @Query("SELECT t FROM Ticket t WHERE t.price BETWEEN ?1 and ?2 order by t.price ASC")
    Page<Ticket> findTicketByPrice(Integer min, Integer max, PageRequest pageRequest);

    @Query("SELECT DISTINCT t.location FROM Ticket t")
    List<String> getAllLocation();

    @Query("SELECT DISTINCT t.genre FROM Ticket t")
    List<String> getAllGenre();

}
