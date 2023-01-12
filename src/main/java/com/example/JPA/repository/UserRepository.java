package com.example.JPA.repository;

import com.example.JPA.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User,Long> {

//    User findUserByTicketId(Long ticket_id);
//
//    List<User> findAllByTicketName(String name);

//    @Query(value = "SELECT * FROM User u",nativeQuery = true)
//    List<User> findAll();

    boolean existsUsersById(Long id);
}
