package com.example.JPA.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSearchDto {
    private Optional<String> location = Optional.empty();
    private Optional<Integer> minPrice = Optional.empty();
    private Optional<Integer> maxPrice = Optional.empty();
    private Optional<String> startDate = Optional.empty();
    private Optional<String> endDate = Optional.empty();
    private Optional<String> genre = Optional.empty();
    private int size;
    private int page;
}
