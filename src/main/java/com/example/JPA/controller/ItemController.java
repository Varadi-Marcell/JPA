package com.example.JPA.controller;

import com.example.JPA.dto.CreateItemRequest;
import com.example.JPA.model.Item;
import com.example.JPA.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Void> addItemToCart(@RequestBody CreateItemRequest request) {
        itemService.addItemToCart(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable("id") Long id) {
        itemService.removeItemFromCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<List<Item>> getAllItem(){
        return new ResponseEntity<>(itemService.getAllItem(),HttpStatus.OK);
    }

}
