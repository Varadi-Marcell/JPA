package com.example.JPA.controller.restApi;

import com.example.JPA.dto.UserDto;
import com.example.JPA.model.User;
import com.example.JPA.repository.UserRepository;
import com.example.JPA.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }

    @GetMapping("/sec")
    public List<User> getAllUser2(){
        return userService.getUsers();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> createUser(@RequestBody User user){
        userService.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK) ;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUserById(@PathVariable("id") Long id){
        userService.deleteUserById(id);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(){
        return new ResponseEntity<>(userService.getUserProfile(),HttpStatus.OK);
    }
}
