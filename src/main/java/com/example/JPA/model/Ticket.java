package com.example.JPA.model;

import com.example.JPA.dto.TicketDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

//    @OneToMany(
//            fetch = FetchType.LAZY
//    )
//    @JsonIgnore
//    private List<Item> item = new ArrayList<>();
//    @JsonIgnore
//    @ManyToMany(mappedBy = "ticketList")
//    private List<FestivalCardPass> festivalCardPassList;


//    public void addItem(Item item){
//        if(!this.item.contains(item)){
//            this.item.add(item);
//            item.setTicket(this);
//        }
//    }
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

    public Ticket() {
    }


//    public void setFestivalCardPassList(FestivalCardPass festivalCardPassList) {
//        this.festivalCardPassList.add(festivalCardPassList);
//    }
//
//    public void removeFestivalCardPass(FestivalCardPass festivalCardPass) {
//        this.festivalCardPassList.remove(festivalCardPass);
//        festivalCardPass.getTicketList().remove(this);
//    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
//                ", festivalCardPass=" + festivalCardPassList +
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
