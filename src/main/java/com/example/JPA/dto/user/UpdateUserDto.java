package com.example.JPA.dto.user;

import com.example.JPA.model.CardPass;
import com.example.JPA.model.Role;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class UpdateUserDto {
    private Long id;
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @Min(18)
    @Max(99)
    private int age;
    @NotNull
    private Role role;

}
