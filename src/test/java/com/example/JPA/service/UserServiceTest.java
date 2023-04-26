package com.example.JPA.service;

import com.example.JPA.dto.user.UpdateUserDto;
import com.example.JPA.dto.user.UserDto;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.CardPass;
import com.example.JPA.model.Role;
import com.example.JPA.model.User;
import com.example.JPA.repository.UserRepository;
import com.example.JPA.service.serviceImpl.UserServiceImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserDetailsService userDetailsService;
    @Mock
    EntityManager entityManager;
    @Mock
    SimpMessagingTemplate messagingTemplate;
    List<User> userList;
    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserDetails userDetails;
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
    public void shouldUpdateUser() {
        UpdateUserDto dto = new UpdateUserDto();
        dto.setId(Optional.of(1L));
        dto.setName("marci");
        dto.setEmail("mail@mail.com");
        dto.setRole(Role.USER);
        dto.setAge(32);

        User user = new User("John Doe", "john.doe@mail.com", "encodedPassword", 30, Role.USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);

        UserDto updatedUserDto = userService.updateUser(dto);

        verify(userRepository,times(1)).updateUser(dto.getName(),dto.getAge(),dto.getEmail(),dto.getRole(),dto.getId().get());
    }

    @Test
    public void shouldCreateUser() {
        User user = new User("John Doe", "john.doe@mail.com", "encodedPassword", 30, Role.USER);

        when(userRepository.existsUsersByEmail(any())).thenReturn(false);
        userService.createUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void shouldReturnUser() {
        when(userRepository.findById(1l)).thenReturn(Optional.ofNullable(userList.get(0)));
        User user = userService.getUserById(1l);
        assertEquals(userList.get(0), user);
    }

    @Test
    public void shouldThrowResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(1l);
        });
    }
}
