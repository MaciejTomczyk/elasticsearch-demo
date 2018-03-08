package com.mt.es.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestBuilderTest {

    @Mock
    private RestHighLevelClient restClient;

    @InjectMocks
    private RequestBuilder requestBuilder;

    @Test
    public void shouldSendRequest() throws IOException {
        // given
        SearchRequest request = mock(SearchRequest.class,RETURNS_DEEP_STUBS);
        SearchResponse response = mock(SearchResponse.class,RETURNS_DEEP_STUBS);
        when(restClient.search(any())).thenReturn(response);

        // when
        Optional<SearchResponse> result = requestBuilder.sendRequest(request);

        // then
        verify(restClient,atLeastOnce()).search(any());
        assertThat(result.orElse(null),is(response));
    }

    @Test
    public void shouldSendRequestAndThrowException() throws IOException {
        // given
        SearchRequest request = mock(SearchRequest.class,RETURNS_DEEP_STUBS);
        when(restClient.search(any())).thenThrow(IOException.class);

        // when
        Optional<SearchResponse> result = requestBuilder.sendRequest(request);

        // then
        verify(restClient,atLeastOnce()).search(any());
        assertThat(result,is(Optional.empty()));
    }

    @Test
    public void shouldPrepareRequest() {
        // given
        BoolQueryBuilder qb = boolQuery();

        // when
        SearchRequest result = requestBuilder.prepareRequest(qb);

        // then
        assertNotNull(result);
    }

    @Test
    public void shouldPrepareRequestWithAggregation() {
        // given
        BoolQueryBuilder qb = boolQuery();
        AggregationBuilder ab = mock(AggregationBuilder.class);

        // when
        SearchRequest result = requestBuilder.prepareRequestWithAggregation(qb,ab);

        // then
        assertNotNull(result);
        assertEquals(1,result.source().aggregations().count());
    }
}
