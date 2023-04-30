package com.example.JPA.dto.ticket;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    private Long id;

    private String name;

    private String location;

    private Integer price;

    private LocalDate startDate;

    private LocalDate endDate;

    private String genre;

}
