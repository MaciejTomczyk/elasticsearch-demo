package com.mt.es.rest;

import com.mt.es.model.Cat;
import com.mt.es.model.CatSearchWrapper;
import com.mt.es.service.CatIndexer;
import com.mt.es.service.CatSearch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatControllerTest {

    @Mock
    private CatIndexer index;

    @Mock
    private CatSearch search;

    @InjectMocks
    private CatController controller = new CatController();


    @Test
    public void shouldCreateIndex() {
        // given
        doNothing().when(index).createIndex();

        // when
        ResponseEntity response = controller.createIndex();

        // then
        assertThat(response.getStatusCode(),is(HttpStatus.OK));
    }

    @Test
    public void shouldIndexCats() {
        // given
        doNothing().when(index).indexCats();

        // when
        ResponseEntity response = controller.indexCats();

        // then
        assertThat(response.getStatusCode(),is(HttpStatus.OK));
    }

    @Test
    public void shouldFindCats() {
        // given
        CatSearchWrapper searchFilter = new CatSearchWrapper();
        searchFilter.setNames(Arrays.asList("A","B"));
        Cat c1 = new Cat();
        c1.setName("A");
        Cat c2 = new Cat();
        c2.setName("B");
        List<Cat> cats = Arrays.asList(c1,c2);
        when(search.findBySearchFilter(searchFilter)).thenReturn(cats);

        // when
        ResponseEntity response = controller.findCats(searchFilter);

        // then
        assertThat(response.getBody(),is(cats));
    }

    @Test
    public void shouldAggregateCats() {
        // given
        CatSearchWrapper searchFilter = new CatSearchWrapper();
        searchFilter.setNames(Arrays.asList("A","B"));
        List<String> aggs = Arrays.asList("A:10","B:1");
        when(search.aggregateByField(searchFilter)).thenReturn(aggs);

        // when
        ResponseEntity response = controller.aggregateCats(searchFilter);

        // then
        assertThat(response.getBody(),is(aggs));
    }
}
