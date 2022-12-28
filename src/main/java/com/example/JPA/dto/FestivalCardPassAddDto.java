package com.example.JPA.dto;

import com.example.JPA.model.FestivalCardPass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FestivalCardPassAddDto implements Serializable {

    private Long id;
    private FestivalCardPass festivalCardPass;

}
