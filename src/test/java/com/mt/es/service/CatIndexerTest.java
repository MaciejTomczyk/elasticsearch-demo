package com.mt.es.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.es.model.Cat;
import org.apache.http.entity.StringEntity;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;

import static org.apache.commons.lang3.reflect.FieldUtils.writeDeclaredField;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatIndexerTest {

    @Mock
    private RestHighLevelClient restClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private IndexCreator indexCreator;

    @InjectMocks
    private CatIndexer indexer = new CatIndexer();

    @Test
    public void shouldCreateIndex() throws IOException {
        // given
        RestClient client = mock(RestClient.class, RETURNS_DEEP_STUBS);
        when(restClient.getLowLevelClient()).thenReturn(client);
        when(indexCreator.prepareCatIndex()).thenReturn("");

        // when
        indexer.createIndex();

        // then
        verify(client, atLeastOnce()).performRequest(anyString(), anyString(), anyMap(), any(StringEntity.class));
    }

    @Test
    public void shouldCreateIndexAndThrowException() throws IOException {
        // given
        RestClient client = mock(RestClient.class, RETURNS_DEEP_STUBS);
        when(restClient.getLowLevelClient()).thenReturn(client);
        when(client.performRequest(anyString(), anyString(), anyMap(), any(StringEntity.class))).thenThrow(IOException.class);
        when(indexCreator.prepareCatIndex()).thenReturn("");

        // when
        indexer.createIndex();

        // then
        verify(client, atLeastOnce()).performRequest(anyString(), anyString(), anyMap(), any(StringEntity.class));
    }

    @Test
    public void shouldIndexCats() throws IOException, IllegalAccessException {
        // given
        writeDeclaredField(indexer,"cats", Arrays.asList(new Cat(),new Cat()),true);
        IndexResponse response = mock(IndexResponse.class);
        when(mapper.writeValueAsString(any())).thenReturn("");
        when(restClient.index(any())).thenReturn(response);

        // when
        indexer.indexCats();

        // then
        verify(restClient, atLeast(2)).index(any());
    }

    @Test
    public void indexCatsAndThrowException() throws IOException, IllegalAccessException {
        // given
        writeDeclaredField(indexer,"cats", Arrays.asList(new Cat(),new Cat()),true);
        when(mapper.writeValueAsString(any())).thenReturn("");
        when(restClient.index(any())).thenThrow(IOException.class);

        // when
        indexer.indexCats();

        // then
        verify(restClient, atLeast(2)).index(any());
    }
}
