package com.example.JPA.model;

import com.example.JPA.dto.TicketDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;
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

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    //    @JsonIgnore
//    @ManyToMany(
//            fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "festivalCardPass_id",
//            referencedColumnName = "id",
//            foreignKey = @ForeignKey(
//                    name = "festivalCardPass_id_fk"
//            )
//    )
    @JsonIgnore
    @ManyToMany(mappedBy = "ticketList")
    private List<FestivalCardPass> festivalCardPass;

    public Ticket(Long id, String name, String location, LocalDateTime startDate, Integer price, LocalDateTime endDate) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setFestivalCardPass(FestivalCardPass festivalCardPass) {
        this.festivalCardPass.add(festivalCardPass);
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<FestivalCardPass> getFestivalCardPass() {
        return festivalCardPass;
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
                ", festivalCardPass=" + festivalCardPass +
                '}';
    }

    public TicketDto ticketToTicketDto(Ticket ticket) {

        return new TicketDto(ticket.getId(),
                ticket.getName(),
                ticket.getLocation(),
                ticket.getPrice(),
                ticket.getStartDate(),
                ticket.getEndDate()
        );
    }
}
