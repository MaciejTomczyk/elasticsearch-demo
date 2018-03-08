package com.mt.es.service;

import com.mt.es.model.Cat;
import com.mt.es.model.CatSearchWrapper;
import lombok.extern.apachecommons.CommonsLog;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

@Service
@CommonsLog
public class CatSearch {

    @Autowired
    private RequestBuilder requestBuilder;

    @Autowired
    private QueryBuilder queryBuilder;

    @Autowired
    private ResponseBuilder responseBuilder;

    public List<Cat> findBySearchFilter(CatSearchWrapper search) {
        BoolQueryBuilder query = boolQuery();
        Optional.ofNullable(search.getNames()).ifPresent(i -> queryBuilder.findByName(query, i));
        Optional.ofNullable(search.getInitial()).ifPresent(i -> queryBuilder.findByChar(query, i.charAt(0)));
        Optional.ofNullable(search.getId()).ifPresent(i -> queryBuilder.findByLookupQuery(query, i));

        queryBuilder.findInRange(query, search);
        return requestBuilder.sendRequest(requestBuilder.prepareRequest(query))
                .map(i -> responseBuilder.parseResponse(i))
                .orElse(new ArrayList<>());
    }

    public List<String> aggregateByField(CatSearchWrapper search) {
        BoolQueryBuilder query = boolQuery();
        return requestBuilder.sendRequest(requestBuilder.prepareRequestWithAggregation(query, queryBuilder.groupBy(search.getGroupBy())))
                .map(i -> responseBuilder.parseAggregation(i, search.getGroupBy()))
                .orElse(new ArrayList<>());
    }


}
