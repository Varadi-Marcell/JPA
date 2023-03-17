package com.example.JPA.controller.restApi;

import com.example.JPA.dto.CartDto;
import com.example.JPA.dto.CreateItemRequest;
import com.example.JPA.dto.ItemDto;
import com.example.JPA.dto.UpdateItemDto;
import com.example.JPA.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    @GetMapping("{id}")
    public ResponseEntity<Optional<CartDto>> getCartById(@PathVariable("id") Long id){
        return new ResponseEntity<>(cartService.getShoppingCartById(id), HttpStatus.OK);
    }
    @GetMapping("/view-cart")
    public ResponseEntity<Optional<CartDto>> viewCart(){
        return new ResponseEntity<>(cartService.viewCart(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addItemToCart(@RequestBody CreateItemRequest request){
        cartService.addItemToCart(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{itemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable("itemId") Long itemId){
        cartService.removeItemFromCartByItemId(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> updateItem(@RequestBody UpdateItemDto itemDto){
        cartService.updateItemQuantity(itemDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
