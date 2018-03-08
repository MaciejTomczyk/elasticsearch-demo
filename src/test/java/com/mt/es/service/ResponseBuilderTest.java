package com.mt.es.service;

import com.mt.es.model.Cat;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResponseBuilderTest {

    @Mock
    private CatMapper mapper;

    @InjectMocks
    private ResponseBuilder responseBuilder = new ResponseBuilder();

    @Test
    public void shouldParseResponse() {
        // given
        SearchResponse response = mock(SearchResponse.class,RETURNS_DEEP_STUBS);
        SearchHit hit = mock(SearchHit.class);
        SearchHit[] hits = new SearchHit[]{hit};
        Cat c = new Cat();
        when(response.getHits().getHits()).thenReturn(hits);
        when(mapper.convert(any())).thenReturn(Optional.of(c));

        // when
        List<Cat> result = responseBuilder.parseResponse(response);

        // then
        verify(mapper,atLeastOnce()).convert(any());
        assertThat(result.get(0),is(c));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldParseAggregation() {
        // given
        SearchResponse response = mock(SearchResponse.class,RETURNS_DEEP_STUBS);
        ParsedStringTerms terms = mock(ParsedStringTerms.class,RETURNS_DEEP_STUBS);
        Terms.Bucket bucket = mock(Terms.Bucket.class);
        List buckets = Collections.singletonList(bucket);
        String groupBy = "eyeColour";
        when(response.getAggregations().get(any())).thenReturn(terms);
        when(terms.getBuckets()).thenReturn(buckets);
        when(bucket.getKeyAsString()).thenReturn("A");
        when(bucket.getDocCount()).thenReturn(10L);

        // when
        List<String> result = responseBuilder.parseAggregation(response, groupBy);

        // then
        assertEquals(1,result.size());
    }
}
