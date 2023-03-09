package com.example.JPA.dto;

import com.example.JPA.model.CardPass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;

    private String email;
    private int age;
    private String role;

    private CardPass cardPass;

}
