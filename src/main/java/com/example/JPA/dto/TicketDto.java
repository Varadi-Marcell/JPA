package com.example.JPA.dto;

import com.example.JPA.model.FestivalCardPass;
import com.example.JPA.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    private Long id;

    private String name;

    private String location;

    private Integer price;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

//    private FestivalCardPass festivalCardPass;
}
