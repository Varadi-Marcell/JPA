package com.example.JPA.model;


import com.example.JPA.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity(name = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "users",
        uniqueConstraints= {
        @UniqueConstraint(
                name = "user_name_unique",
                columnNames = "name")
}
)
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "name",
            nullable = false,
            columnDefinition = "TEXT")
    private String name;

    @Column(name = "age")
    private int age;

    //    @JsonIgnore
    @OneToOne(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private FestivalCardPass festivalCardPass;


    public FestivalCardPass getFestivalCardPass() {
        return festivalCardPass;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }



    public void setFestivalCardPass(FestivalCardPass festivalCardPass) {
        this.festivalCardPass = festivalCardPass;
    }

    public UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.age,
                user.getFestivalCardPass()
        );
    }
}
