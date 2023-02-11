package com.example.JPA.model;

import com.example.JPA.dto.TicketDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
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

    @JsonIgnore
    @ManyToMany(mappedBy = "ticketList")
    private List<FestivalCardPass> festivalCardPassList;

    public Ticket(Long id, String name, String location, LocalDate startDate, Integer price, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.price = price;
        this.endDate = endDate;
    }

    public Ticket(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Ticket() {
    }


    public void setFestivalCardPassList(FestivalCardPass festivalCardPassList) {
        this.festivalCardPassList.add(festivalCardPassList);
    }

    public void removeFestivalCardPass(FestivalCardPass festivalCardPass) {
        this.festivalCardPassList.remove(festivalCardPass);
        festivalCardPass.getTicketList().remove(this);
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
                ", festivalCardPass=" + festivalCardPassList +
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
