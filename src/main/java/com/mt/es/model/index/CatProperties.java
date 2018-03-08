package com.mt.es.model.index;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CatProperties {

    private IndexField birthDate;
    private IndexField breed;
    private IndexField eyeColour;
    private IndexField furColour;
    private IndexField gender;
    private IndexField interests;
    private IndexField name;
}

