package com.example.JPA.controller.restApiControllers;

import com.example.JPA.dto.user.UpdateUserDto;
import com.example.JPA.dto.user.UserDto;
import com.example.JPA.model.User;
import com.example.JPA.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }


    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile() {
        return new ResponseEntity<>(userService.getUserProfile(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UpdateUserDto dto) {
        return new ResponseEntity<>(userService.updateUser(dto), HttpStatus.CREATED);
    }
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") Long id){
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
