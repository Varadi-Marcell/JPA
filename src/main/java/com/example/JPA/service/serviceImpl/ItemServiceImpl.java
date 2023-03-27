package com.example.JPA.service.serviceImpl;

import com.example.JPA.model.Item;
import com.example.JPA.repository.ItemRepository;
import com.example.JPA.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public List<Item> getAllItem() {
        return itemRepository.findAll();
    }
}
