package com.mt.es.service;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.http.Header;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static com.mt.es.config.CatConstants.CATS;
import static com.mt.es.config.CatConstants.ID;

@Service
@CommonsLog
public class RequestBuilder {

    @Autowired
    private RestHighLevelClient restClient;

    public Optional<SearchResponse> sendRequest(SearchRequest request, Header... headers) {
        try {
            return Optional.ofNullable(restClient.search(request, headers));
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
        return Optional.empty();
    }

    public SearchRequest prepareRequest(BoolQueryBuilder query) {
        SearchRequest search = new SearchRequest(CATS.value());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.sort(SortBuilders.fieldSort(ID.value()).order(SortOrder.ASC));
        search.source(searchSourceBuilder);
        return search;
    }

    public SearchRequest prepareRequestWithAggregation(BoolQueryBuilder query, AggregationBuilder agg) {
        SearchRequest search = new SearchRequest(CATS.value());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.sort(SortBuilders.fieldSort(ID.value()).order(SortOrder.ASC));
        searchSourceBuilder.aggregation(agg);
        search.source(searchSourceBuilder);
        return search;
    }
}

