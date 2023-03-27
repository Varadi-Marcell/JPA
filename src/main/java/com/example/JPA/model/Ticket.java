package com.example.JPA.model;

import com.example.JPA.dto.ticket.TicketDto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String location;
    private Integer price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String genre;
    private Boolean isAvailable;
    private Boolean isUpcoming;
    private Boolean isLimited;
    public Ticket() {
    }

    public Ticket(Long id, String name, String location, LocalDate startDate, Integer price, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public Ticket(String name, String location, LocalDate startDate, LocalDate endDate, Integer price, String genre) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.genre = genre;
    }

    public Ticket(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public TicketDto ticketToTicketDto(Ticket ticket) {

        return new TicketDto(ticket.getId(),
                ticket.getName(),
                ticket.getLocation(),
                ticket.getPrice(),
                ticket.getStartDate(),
                ticket.getEndDate(),
                ticket.getGenre(),
                ticket.getIsAvailable(),
                ticket.getIsUpcoming(),
                ticket.getIsLimited()

        );
    }
}
