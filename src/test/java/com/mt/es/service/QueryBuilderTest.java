package com.mt.es.service;

import com.mt.es.model.CatSearchWrapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QueryBuilderTest {

    private QueryBuilder queryBuilder = new QueryBuilder();

    @Test
    public void shouldFindInRange() {
        // given
        BoolQueryBuilder qb = boolQuery();
        CatSearchWrapper search = new CatSearchWrapper();
        search.setFrom("2017-01-01");
        search.setTo("2017-01-01");
        // when
        queryBuilder.findInRange(qb, search);

        // then
        assertEquals(1,qb.filter().size());
        assertTrue(qb.filter().get(0).toString().contains("birthDate"));
        assertTrue(qb.filter().get(0).toString().contains("from"));
        assertTrue(qb.filter().get(0).toString().contains("to"));
    }

    @Test
    public void shouldFindInRangeEmptyDates() {
        // given
        BoolQueryBuilder qb = boolQuery();
        CatSearchWrapper search = new CatSearchWrapper();
        // when
        queryBuilder.findInRange(qb, search);

        // then
        assertEquals(0,qb.filter().size());
    }

    @Test
    public void shouldFindInRangeEmptyTo() {
        // given
        BoolQueryBuilder qb = boolQuery();
        CatSearchWrapper search = new CatSearchWrapper();
        search.setFrom("2017-01-01");
        // when
        queryBuilder.findInRange(qb, search);

        // then
        assertEquals(0,qb.filter().size());
    }

    @Test
    public void shouldFindInRangeEmptyFrom() {
        // given
        BoolQueryBuilder qb = boolQuery();
        CatSearchWrapper search = new CatSearchWrapper();
        search.setTo("2017-01-01");
        // when
        queryBuilder.findInRange(qb, search);


        // then
        assertEquals(0,qb.filter().size());
    }

    @Test
    public void shouldFindByName() {
        // given
        BoolQueryBuilder qb = boolQuery();
        // when
        queryBuilder.findByName(qb, Collections.singletonList("A"));

        // then
        assertEquals(1,qb.filter().size());
        assertTrue(qb.filter().get(0).toString().contains("name.keyword"));
    }

    @Test
    public void shouldFindByChar() {
        // given
        BoolQueryBuilder qb = boolQuery();
        // when
        queryBuilder.findByChar(qb, 'M');

        // then
        assertEquals(1,qb.should().size());
        assertTrue(qb.should().get(0).toString().contains("wildcard"));
        assertTrue(qb.should().get(0).toString().contains("M*"));
    }

    @Test
    public void shouldFindByLookupQuery() {
        // given
        BoolQueryBuilder qb = boolQuery();
        // when
        queryBuilder.findByLookupQuery(qb, 1L);

        // then
        assertEquals(1,qb.filter().size());
        assertTrue(qb.filter().get(0).toString().contains("furColour.keyword"));
        assertTrue(qb.filter().get(0).toString().contains("eyeColour"));
    }

    @Test
    public void shouldGroupBy() {
        // given
        String groupBy ="eyeColour";
        // when
        AggregationBuilder result = queryBuilder.groupBy(groupBy);

        // then
        assertNotNull(result);
        assertEquals(groupBy,result.getName());
    }
}
