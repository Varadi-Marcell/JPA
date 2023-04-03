package com.example.JPA.service;

import com.example.JPA.dto.user.UpdateUserDto;
import com.example.JPA.dto.user.UserDto;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.CardPass;
import com.example.JPA.model.Role;
import com.example.JPA.model.User;
import com.example.JPA.repository.UserRepository;
import com.example.JPA.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserDetailsService userDetailsService;
    @Mock
    SimpMessagingTemplate messagingTemplate;
    List<User> userList;
    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void init() {

        userList = new ArrayList<>();
        userList.add(new User("John Doe", "john.doe@mail.com", "encodedPassword", 30, Role.USER));
        userList.add(new User("John Doe2", "john.doe2@mail.com", "encodedPassword", 30, Role.USER));
        CardPass pass = new CardPass(4324, userList.get(0));
        userList.get(0).setCardPass(pass);
    }

    @Test
    public void shouldGetAllUser() {
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(userService.getAllUser().size(), userList.size());
    }

    @Test
    public void getUserProfile() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("mocked@example.com");

        SecurityContextHolder.setContext(securityContext);
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        assertEquals("mocked@example.com", userEmail);

        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(userList.get(0)));
        userService.getUserProfile();

        verify(userRepository, times(1)).findByEmail(any());

    }

    @Test
    public void updateUser() {
        UpdateUserDto userDto = new UpdateUserDto();
        userDto.setId(1L);
        userDto.setName("marci");
        userDto.setEmail("marci@gmail.com");
        userDto.setAge(32);
        userDto.setRole(Role.USER);

        User user = new User("John Doe", "john.doe@mail.com", "encodedPassword", 30, Role.USER);
        user.setId(1L);
        user.setCardPass(new CardPass(432, user));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);

        UserDto updatedUser = userService.updateUser(userDto);
        verify(userRepository, times(1)).save(any());
        assertEquals(updatedUser.getName(), userDto.getName());
        assertEquals(updatedUser.getEmail(), userDto.getEmail());
        assertEquals(updatedUser.getAge(), userDto.getAge());
        assertEquals(String.valueOf(updatedUser.getRole()), String.valueOf(userDto.getRole()));

    }
    @Test
    public void shouldCreateUser(){
        User user = new User("John Doe", "john.doe@mail.com", "encodedPassword", 30, Role.USER);

        when(userRepository.existsUsersByEmail(any())).thenReturn(false);
        userService.createUser(user);
        verify(userRepository,times(1)).save(user);
    }
    @Test
    public void shouldReturnUser(){
        when(userRepository.findById(1l)).thenReturn(Optional.ofNullable(userList.get(0)));
        User user =userService.getUserById(1l);
        assertEquals(userList.get(0),user);
    }
    @Test
    public void shouldThrowResourceNotFoundException(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById (1l);
        });
    }
}
