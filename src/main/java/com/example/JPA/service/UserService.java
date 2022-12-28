package com.example.JPA.service;

import com.example.JPA.model.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUser();
    public void createUser(User user);
    public User getUserById(Long id);
    public void deleteUserById(Long id);

}
