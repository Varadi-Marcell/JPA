package com.example.JPA.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FestivalCardPassAddDto implements Serializable {

    private Long id;
    private String name;
    private Integer amount;

}
