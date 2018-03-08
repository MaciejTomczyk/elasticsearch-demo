package com.mt.es.service;

import com.mt.es.model.Cat;
import com.mt.es.model.CatSearchWrapper;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatSearchTest {

    @Mock
    private RequestBuilder requestBuilder;

    @Mock
    private QueryBuilder queryBuilder;

    @Mock
    private ResponseBuilder responseBuilder;

    @InjectMocks
    private CatSearch catSearch = new CatSearch();

    @Test
    public void findBySearchFilter() {
        // given
        CatSearchWrapper searchFilter = new CatSearchWrapper();
        searchFilter.setNames(Arrays.asList("A","B"));
        searchFilter.setInitial("C");
        searchFilter.setId(1L);
        searchFilter.setFrom("2017-01-01");
        searchFilter.setTo("2017-01-01");
        SearchResponse response = mock(SearchResponse.class);
        Cat c = new Cat();
        List<Cat> cats = Collections.singletonList(c);
        doNothing().when(queryBuilder).findByName(any(),any());
        doNothing().when(queryBuilder).findByChar(any(),anyChar());
        doNothing().when(queryBuilder).findByLookupQuery(any(),any());
        doNothing().when(queryBuilder).findInRange(any(),any());
        when(requestBuilder.sendRequest(any())).thenReturn(Optional.of(response));
        when(responseBuilder.parseResponse(any())).thenReturn(cats);

        // when
        List<Cat> result = catSearch.findBySearchFilter(searchFilter);

        // then
        verify(queryBuilder,atLeastOnce()).findByName(any(),any());
        verify(queryBuilder,atLeastOnce()).findByChar(any(),anyChar());
        verify(queryBuilder,atLeastOnce()).findByLookupQuery(any(),any());
        verify(queryBuilder,atLeastOnce()).findInRange(any(),any());
        verify(requestBuilder,atLeastOnce()).sendRequest(any(),any());
        verify(responseBuilder,atLeastOnce()).parseResponse(any());
        assertThat(result,is(cats));
    }

    @Test
    public void aggregateByField() {
        // given
        CatSearchWrapper searchFilter = new CatSearchWrapper();
        SearchResponse response = mock(SearchResponse.class);
        searchFilter.setGroupBy("eyeColour");
        List<String> aggList = Arrays.asList("A:10","B:1");
        when(requestBuilder.sendRequest(any())).thenReturn(Optional.of(response));
        when(responseBuilder.parseAggregation(any(),any())).thenReturn(aggList);
        // when

        List<String> aggs = catSearch.aggregateByField(searchFilter);

        // then
        verify(requestBuilder,atLeastOnce()).sendRequest(any());
        verify(responseBuilder,atLeastOnce()).parseAggregation(any(),any());
        assertThat(aggs,is(aggList));
    }
}
