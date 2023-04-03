package com.example.JPA.service.serviceImpl;

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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final SimpMessagingTemplate messagingTemplate;


    public UserServiceImpl(UserRepository userRepository, UserDetailsService userDetailsService, SimpMessagingTemplate messagingTemplate) {
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
        Optional<User> user = userRepository.findById(userDto.getId());
        if (user.isEmpty()){
            throw new ResourceNotFoundException("User not found");
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.get().getEmail());
        messagingTemplate.convertAndSendToUser(
                userDetails.getUsername(),
                "/topic/private/userdata",
                "Your profile has been updated by the admin!");

        user = Optional.ofNullable(User.builder()
                        .name(userDto.getName())
                        .email(userDto.getEmail())
                        .age(userDto.getAge())
                        .role(userDto.getRole())
                .build());
        userRepository.save(user.get());
        return user.get().convertToDto(user.get());
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
