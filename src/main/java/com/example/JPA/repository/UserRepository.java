package com.example.JPA.repository;

import com.example.JPA.model.Role;
import com.example.JPA.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsUsersById(Long id);

    boolean existsUsersByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name =?1,u.age = ?2,u.email = ?3, u.role =?4 WHERE u.id = ?5")
    void updateUser(String name, int age, String email, Role role , Long id);


}
