package com.mt.es.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.es.model.Cat;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@CommonsLog
public class CatMapper {

    @Autowired
    private ObjectMapper mapper;

    public Optional<Cat> convert(String hitSource) {
        Optional<Cat> cat = Optional.empty();
        try {
            cat = Optional.ofNullable(mapper.readValue(hitSource, Cat.class));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return cat;
    }
}

