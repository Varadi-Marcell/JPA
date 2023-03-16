package com.example.JPA.repository;

import com.example.JPA.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    public void deleteItemsByTicketId(Long ticketId);
    public List<Item> findItemsByTicketId(Long ticketId);

}
