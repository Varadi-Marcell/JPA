package com.example.JPA.service;

import com.example.JPA.dto.user.UpdateUserDto;
import com.example.JPA.dto.user.UserDto;
import com.example.JPA.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService {

    public List<UserDto> getAllUser();

    public UserDto getUserProfile();

    public void createUser(User user);

    public User getUserById(Long id);

    public void deleteUserById(Long id);

    public User getUser();
    public UserDto updateUser(UpdateUserDto userDto);


}
