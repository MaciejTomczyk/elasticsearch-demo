package com.mt.es.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.es.model.index.CatProperties;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IndexCreatorTest {


    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private IndexCreator indexCreator = new IndexCreator();

    @Test
    public void prepareCatIndex() throws JsonProcessingException {
        // given
        when(mapper.writeValueAsString(any())).thenReturn("");

        // when
        String result = indexCreator.prepareCatIndex();

        // then
        assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void prepareCatIndexAndThrowJsonException() throws JsonProcessingException {
        // given
        when(mapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        // when
        indexCreator.prepareCatIndex();

        // then
    }

    @Test(expected = IllegalArgumentException.class)
    public void prepareCatIndexAndThrowFieldException() throws Throwable {
        // given
        Field f = mock(Field.class);
        when(f.getName()).thenReturn("thisFieldDoesNotExist");

        // when
        try {
            MethodUtils.invokeMethod(indexCreator, true, "fillField", new CatProperties(), f);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }

        // then
    }
}
