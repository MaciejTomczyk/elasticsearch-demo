package com.mt.es.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.es.model.Cat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatMapperTest {

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private CatMapper catMapper = new CatMapper();

    @Test
    @SuppressWarnings("unchecked")
    public void shouldConvert() throws IOException {
        // given
        Cat c = new Cat();
        when(mapper.readValue(anyString(), (Class<Object>) any())).thenReturn(c);

        // when
        Optional<Cat> result = catMapper.convert("");

        // then
        verify(mapper,atLeastOnce()).readValue(anyString(),(Class<Object>) any());
        assertThat(result.orElse(null),is(c));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldConvertAndThrowException() throws IOException {
        // given
        when(mapper.readValue(anyString(), (Class<Object>) any())).thenThrow(IOException.class);

        // when
        Optional<Cat> result = catMapper.convert("");

        // then
        verify(mapper,atLeastOnce()).readValue(anyString(),(Class<Object>) any());
        assertThat(result,is(Optional.empty()));
    }
}
