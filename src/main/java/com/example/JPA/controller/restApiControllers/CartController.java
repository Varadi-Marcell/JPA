package com.example.JPA.controller.restApiControllers;

import com.example.JPA.dto.CartDto;
import com.example.JPA.dto.item.CreateItemRequest;
import com.example.JPA.dto.item.UpdateItemDto;
import com.example.JPA.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("{id}")
    public ResponseEntity<Optional<CartDto>> getCartById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cartService.getShoppingCartById(id), HttpStatus.OK);
    }

    @GetMapping("/view-cart")
    public ResponseEntity<Optional<CartDto>> viewCart() {
        return new ResponseEntity<>(cartService.viewCart(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addItemToCart(@RequestBody CreateItemRequest request) {
        cartService.addItemToCart(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{itemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable("itemId") Long itemId, Principal principal) {

        CartDto cart = cartService.removeItemFromCartByItemId(itemId).get();
        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/update-cart", cart);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping
    public ResponseEntity<?> updateItem(
            @RequestBody UpdateItemDto itemDto,
            Principal principal
    ) {
        CartDto cart = cartService.updateItemQuantity(itemDto).get();

        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/update-cart", cart);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
