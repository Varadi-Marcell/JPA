package com.example.JPA.service.serviceImpl;

import com.example.JPA.config.JwtService;
import com.example.JPA.dto.user.UpdateUserDto;
import com.example.JPA.dto.user.UserDto;
import com.example.JPA.exceptions.EmailAlreadyExistsException;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.User;
import com.example.JPA.repository.UserRepository;
import com.example.JPA.service.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final SimpMessagingTemplate messagingTemplate;


    public UserServiceImpl(JwtService jwtService, UserRepository userRepository, UserDetailsService userDetailsService, SimpMessagingTemplate messagingTemplate) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.messagingTemplate = messagingTemplate;
    }


    public List<UserDto> getAllUser() {
        return userRepository.findAll().stream()
                .map(s -> s.convertToDto(s))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserProfile() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.of(userRepository.findByEmail(userEmail)
                        .map(user -> new UserDto().toUserDto(user, user.getCardPass(), user.getCardPass().getOrders()))
                        .get())
                .orElseThrow(() -> new RuntimeException("Server error"));
    }

    @Override
    public User getUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserDto updateUser(UpdateUserDto userDto) {
        User user1 = userRepository.findById(userDto.getId()).get();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user1.getEmail());

        System.out.println(userDetails.getUsername());
        messagingTemplate.convertAndSendToUser(
                userDetails.getUsername(),
                "/topic/private/userdata",
                "Your profile has been updated by the admin! \n Please log in again! ");

        return userRepository.findById(userDto.getId())
                .map(user -> {
                    user.setName(userDto.getName());
                    user.setEmail(userDto.getEmail());
                    user.setAge(userDto.getAge());
                    user.setRole(userDto.getRole());
                    return userRepository.save(user);
                })
                .map(user -> user.convertToDto(user))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }


    public void createUser(User user) {
        if (userRepository.existsUsersByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("This email:" + user.getEmail() + " already exists!");
        }
        userRepository.save(user);

    }

    public User getUserById(Long id) {
        return Optional.of(userRepository.findById(id))
                .get()
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + id + " not found!"));
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsUsersById(id)) {
            throw new ResourceNotFoundException("User with id:" + id + " not found!");
        }
        userRepository.deleteById(id);
    }
}
