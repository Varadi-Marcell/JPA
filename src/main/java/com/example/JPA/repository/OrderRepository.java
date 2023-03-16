package com.example.JPA.repository;

import com.example.JPA.model.Orders;
import com.example.JPA.model.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Long> {
    public List<Orders> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate startDate, LocalDate endDate);

    @Query("SELECT o FROM Orders o JOIN o.cardPass c JOIN c.user u WHERE u = :user AND o.startDate BETWEEN :startDate AND :endDate")
    public List<Orders> filterDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("user") User user);
}
