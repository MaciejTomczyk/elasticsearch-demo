package com.mt.es.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CatSearchWrapper {

    private Long id;
    private String from;
    private String to;
    private String groupBy;
    private List<String> names;
    private String initial;
    private List<String> birthDates;
    private List<String> eyeColours;
    private List<String> furColours;
    private List<String> genders;
    private List<String> breeds;
    private List<String> interests;
}

