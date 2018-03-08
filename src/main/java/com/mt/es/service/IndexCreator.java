package com.mt.es.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.es.model.index.CatIndex;
import com.mt.es.model.index.CatProperties;
import com.mt.es.model.index.CatRoot;
import com.mt.es.model.index.Index;
import com.mt.es.model.index.IndexField;
import com.mt.es.model.index.Keyword;
import com.mt.es.model.index.KeywordWrapper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;

import static com.mt.es.config.CatConstants.BIRTH_DATE;
import static com.mt.es.config.CatConstants.DATE;
import static com.mt.es.config.CatConstants.KEYWORD;
import static com.mt.es.config.CatConstants.TEXT;

@Service
@CommonsLog
public class IndexCreator {

    private static final int IGNORE_ABOVE = 256;

    @Autowired
    private ObjectMapper mapper;

    // Index creates itself while indexing an object but you might want to create it yourself for some reason
    public String prepareCatIndex() {
        Index index = new Index();
        CatIndex catIndex = new CatIndex();
        CatRoot catRoot = new CatRoot();
        CatProperties properties = fillProperties();
        catRoot.setProperties(properties);
        catIndex.setCat(catRoot);
        index.setMappings(catIndex);

        try {
            return mapper.writeValueAsString(index);
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
    }

    private CatProperties fillProperties() {
        CatProperties properties = new CatProperties();
        Arrays.stream(properties.getClass().getDeclaredFields()).filter(i -> !i.isSynthetic()).forEach(i -> fillField(properties, i));
        return properties;
    }

    private void fillField(CatProperties properties, Field field) {
        try {
            Field f = CatProperties.class.getDeclaredField(field.getName());
            f.setAccessible(true);
            f.set(properties, prepareField(field.getName()));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private IndexField prepareField(String name) {
        IndexField newField = new IndexField();
        if (name.equals(BIRTH_DATE.value())) {
            newField.setType(DATE.value());
        } else {
            newField.setType(TEXT.value());
            newField.setFields(new KeywordWrapper(new Keyword(KEYWORD.value(), IGNORE_ABOVE)));
        }
        return newField;
    }

}

