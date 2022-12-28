package com.example.JPA.repository;

import com.example.JPA.model.FestivalCardPass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FestivalCardPassRepository extends JpaRepository<FestivalCardPass, Long> {
}
