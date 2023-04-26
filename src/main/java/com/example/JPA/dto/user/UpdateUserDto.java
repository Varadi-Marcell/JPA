package com.example.JPA.dto.user;

import com.example.JPA.model.CardPass;
import com.example.JPA.model.Role;
import com.example.JPA.model.User;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Optional;


@Data
public class UpdateUserDto {
    private Optional<Long> id=Optional.empty();
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

    public User toEntity(UpdateUserDto dto){
        return new User(dto.getName(),dto.email,dto.age, dto.getRole());
    }

}
