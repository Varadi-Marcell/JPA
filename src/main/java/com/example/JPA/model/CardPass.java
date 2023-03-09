package com.example.JPA.model;

import com.example.JPA.dto.FestivalCardpassDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "CardPass")
@Getter
@Setter
public class CardPass {

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

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinTable(
//            name = "ticketList",
//            joinColumns = @JoinColumn(name = "festivalCardPass_id"),
//            inverseJoinColumns = @JoinColumn(name = "ticket_id"))
//    private List<Ticket> ticketList = new ArrayList<>();

    @OneToOne(
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.EAGER,
            mappedBy = "cardPass"
    )
    private Cart cart;

    @JsonIgnore
    @OneToMany(
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "cardPass",
            fetch = FetchType.LAZY
    )
    private List<Orders> orders = new ArrayList<>();

    public CardPass(Integer amount, User user) {
        this.cardHolderName = user.getName();
        this.amount = amount;
        this.user = user;
    }

    public CardPass() {

    }

    @PrePersist
    private void CardPass() {
        this.cart = new Cart(this);
        this.cart.setCardPass(this);
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

//    public List<Ticket> getTicketList() {
//        return ticketList;
//    }
//
//    public void addTicket(Ticket ticket) {
//        this.ticketList.add(ticket);
//        ticket.setFestivalCardPassList(this);
//    }
//
//    public void removeTicket(Ticket ticket) {
//        this.ticketList.remove(ticket);
//        ticket.getFestivalCardPassList().remove(this);
//        ticket.setFestivalCardPassList(null);
//    }
//    public void addOrder(Order order){
//        if(!this.orders.contains(order)){
//            this.orders.add(order);
//            order.setFestivalCardPass(this);
//        }
//    }
//    public void removeOrder(Order order){
//        this.orders.remove(order);
//        order.getFestivalCardPass().re;
//    }


    @Override
    public String toString() {
        return "FestivalCardPass{" +
                "id=" + id +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", amount=" + amount +
//                ", orders=" + orders +
                ", cart=" + cart +
                '}';
    }


    public FestivalCardpassDto convertToDTO(CardPass cardPass) {
        return new FestivalCardpassDto(
                cardPass.getId(),
                cardPass.getCardHolderName(),
                cardPass.getAmount()
//                festivalCardPass.getOrders()
//                festivalCardPass.getTicketList()
        );
    }

}
