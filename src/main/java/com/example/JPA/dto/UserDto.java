package com.example.JPA.dto;

import com.example.JPA.model.FestivalCardPass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private int age;
    private FestivalCardPass festivalCardPass;

}
