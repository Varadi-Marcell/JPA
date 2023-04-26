package com.example.JPA.service.serviceImpl;

import com.example.JPA.dto.user.UpdateUserDto;
import com.example.JPA.dto.user.UserDto;
import com.example.JPA.exceptions.EmailAlreadyExistsException;
import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.User;
import com.example.JPA.repository.UserRepository;
import com.example.JPA.service.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final SimpMessagingTemplate messagingTemplate;
    private final EntityManager entityManager;


    public UserServiceImpl(UserRepository userRepository, UserDetailsService userDetailsService, SimpMessagingTemplate messagingTemplate, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.messagingTemplate = messagingTemplate;
        this.entityManager = entityManager;
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
    public boolean userGuard(Long id) {
        if (!(Objects.equals(getUser().getId(), getUserById(id).getId()))) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public UserDto updateUser(UpdateUserDto userDto) {
        Long id = userDto.getId().orElseGet(() -> getUser().getId());
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!checkMail(user.getEmail(), userDto.getEmail())) {
            notifyUser(user);
        }

        userRepository.updateUser(userDto.getName(), userDto.getAge(), userDto.getEmail(), userDto.getRole(), id);
        entityManager.flush();
        entityManager.refresh(user);

        return user.convertToDto(user);
    }

    public boolean checkMail(String oldEmail, String newEmail) {
        return oldEmail.equals(newEmail);
    }

    public void notifyUser(User user) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getEmail());
        messagingTemplate.convertAndSendToUser(
                userDetails.getUsername(),
                "/topic/private/userdata",
                "Your profile has been updated please relog!");
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
