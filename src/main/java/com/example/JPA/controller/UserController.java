package com.example.JPA.controller;

import com.example.JPA.dto.UserDto;
import com.example.JPA.model.User;
import com.example.JPA.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public List<UserDto> getAllUser(){
        return userService.getAllUser()
                .stream()
                .map(s -> s.convertToDto(s))
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = "application/json")
    public void createUser(@RequestBody User user){
         userService.createUser(user);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK) ;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUserById(@PathVariable("id") Long id){
        userService.deleteUserById(id);
    }

//    @GetMapping("/ticket/{id}")
//    public User getUserByTicketId(@PathVariable("id") Long id){
//        return userServiceimpl.findUserByTicketId(id);
//    }
//    @GetMapping("/ticket/all/{name}")
//    public List<User> getAllUserByTicketId(@PathVariable("name") String name){
//        return userServiceimpl.findAllUsersByTicketName(name);
//    }
}
