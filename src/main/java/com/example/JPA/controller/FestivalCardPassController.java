package com.example.JPA.controller;

import com.example.JPA.dto.FestivalCardpassDto;
import com.example.JPA.service.FestivalCardPassService;
import com.example.JPA.dto.FestivalCardPassAddDto;
import com.example.JPA.model.FestivalCardPass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cardPass")
public class FestivalCardPassController {

    private final FestivalCardPassService festivalCardPassService;

    public FestivalCardPassController(FestivalCardPassService festivalCardPassService){
        this.festivalCardPassService = festivalCardPassService;
    }

    @GetMapping
    public ResponseEntity<List<FestivalCardpassDto>> getAllFestivalCardPass(){
        return new ResponseEntity<>(festivalCardPassService.getAllFestivalCardPass(), HttpStatus.OK);
    }

    @PostMapping
    public void createFestivalCardPass(@RequestBody FestivalCardPassAddDto request){
        festivalCardPassService.createFestivalCardPass(request);
    }

    @PostMapping("/create2")
    public void createFestivalCardPass2(@RequestBody FestivalCardPass festivalCardPass){
        festivalCardPassService.create(festivalCardPass);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletecreateFestivalCardPass(@PathVariable("id") Long id){
        festivalCardPassService.deleteFestivalCard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
