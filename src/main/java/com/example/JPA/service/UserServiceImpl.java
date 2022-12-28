package com.example.JPA.service;

import com.example.JPA.exceptions.ResourceNotFoundException;
import com.example.JPA.model.User;
import com.example.JPA.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUser() {
        return userRepository.findAll();
    }

//    public List<FestivalCardPass> getAllUserWithPass() {
//        return userRepository.findAll().stream().map(User::getFestivalCardPass).collect(Collectors.toList());
//    }


    public void createUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return Optional.of(userRepository.findById(id))
                .get()
                .orElseThrow(() ->new ResourceNotFoundException("User with id:" + id + " not found!"));
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsUsersById(id)) {
            throw new ResourceNotFoundException("User with id:" + id + " not found!");
        }
        userRepository.deleteById(id);
    }
//    public User findUserByTicketId(Long id){
//        User user = userRepository.findUserByTicketId(id);
//        if (user == null){
//            throw new ResourceNotFoundException("User with this ticket id:" + id + " not found!");
//        }
//        return user;
//    }
//
//    public List<User> findAllUsersByTicketName(String name){
//        List<User> userList = userRepository.findAllByTicketName(name);
//        if (userList.isEmpty()){
//            throw new ResourceNotFoundException("Users with this ticket name:" + name + " not found!");
//        }
//        return userList;
//    }
}
