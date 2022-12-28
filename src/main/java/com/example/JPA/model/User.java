package com.example.JPA.model;


import com.example.JPA.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(
        name = "user"
//        uniqueConstraints = {
//                @UniqueConstraint(name = "user_username_unique", columnNames = "username")
//        }
)
public class User implements Serializable {
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

    @Column(name = "username",
            nullable = false,
            columnDefinition = "TEXT")
    private String name;

    @Column(name = "userage")
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

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFestivalCardPass(FestivalCardPass festivalCardPass) {
        this.festivalCardPass = festivalCardPass;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public UserDto convertToDto(User user) {
        return new UserDto(
                user.getName(),
                user.age,
                user.getFestivalCardPass()
        );
    }
}
