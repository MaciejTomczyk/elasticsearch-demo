package com.mt.es.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cat {

    private Long id;
    private String name;
    private String birthDate;
    private String eyeColour;
    private String furColour;
    private String gender;
    private String breed;
    private String interests;
}

