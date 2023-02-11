package com.example.JPA.model;

import com.example.JPA.dto.FestivalCardpassDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "FestivalCardPass")
@Data
public class FestivalCardPass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cardHolderName;

    private Integer amount;

    @JsonIgnore
    @OneToOne(
            fetch = FetchType.LAZY)
    @JoinColumn(
            name = "users_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "users_id_fk"
            )
    )
    private User user;

    //    @ManyToMany(
//            mappedBy = "festivalCardPass",
//            fetch = FetchType.LAZY,
//            cascade = {CascadeType.PERSIST,CascadeType.MERGE}
//    )
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "ticketList",
            joinColumns = @JoinColumn(name = "festivalCardPass_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    private List<Ticket> ticketList = new ArrayList<>();


    public FestivalCardPass(String cardHolderName, Integer amount, User user) {
        this.cardHolderName = cardHolderName;
        this.amount = amount;
        this.user = user;
    }

    public FestivalCardPass() {
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void addTicket(Ticket ticket) {
        this.ticketList.add(ticket);
        ticket.setFestivalCardPassList(this);
    }

    public void removeTicket(Ticket ticket) {
        this.ticketList.remove(ticket);
        ticket.getFestivalCardPassList().remove(this);
        ticket.setFestivalCardPassList(null);
    }

    @Override
    public String toString() {
        return "FestivalCardPass{" +
                "id=" + id +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", amount=" + amount +
                ", user=" + user +
                ", ticketList=" + ticketList +
                '}';
    }


    public FestivalCardpassDto convertToDTO(FestivalCardPass festivalCardPass) {
        return new FestivalCardpassDto(
                festivalCardPass.getId(),
                festivalCardPass.getCardHolderName(),
                festivalCardPass.getAmount(),
                festivalCardPass.getTicketList()
        );
    }

}
