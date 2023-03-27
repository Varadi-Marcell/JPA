package com.example.JPA.controller.restApiControllers;

import com.example.JPA.dto.CardpassDto;
import com.example.JPA.service.CardPassService;
import com.example.JPA.dto.CardPassAddDto;
import com.example.JPA.model.CardPass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cardPass")
public class CardPassController {

    private final CardPassService cardPassService;

    public CardPassController(CardPassService cardPassService){
        this.cardPassService = cardPassService;
    }

    @GetMapping
    public ResponseEntity<List<CardpassDto>> getAllFestivalCardPass(){
        return new ResponseEntity<>(cardPassService.getAllFestivalCardPass(), HttpStatus.OK);
    }

    @PostMapping
    public void createFestivalCardPass(@RequestBody CardPassAddDto request){
        cardPassService.createFestivalCardPass(request);
    }

    @PostMapping("/create2")
    public void createFestivalCardPass2(@RequestBody CardPass cardPass){
        cardPassService.create(cardPass);
    }


}
