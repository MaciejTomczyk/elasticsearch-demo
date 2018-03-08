package com.mt.es.service;

import com.mt.es.model.Cat;
import lombok.extern.apachecommons.CommonsLog;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mt.es.config.CatConstants.SEMICOLON;
import static java.util.stream.Collectors.toList;

@Service
@CommonsLog
public class ResponseBuilder {

    @Autowired
    private CatMapper catMapper;

    public List<Cat> parseResponse(SearchResponse response) {
        List<Cat> list = new ArrayList<>();
        Optional.ofNullable(response)
                .map(this::getHits)
                .map(this::getHits)
                .ifPresent(i -> parseHits(list, i));
        return list;
    }

    public List<String> parseAggregation(SearchResponse response, String groupBy) {

        return Optional.ofNullable(response)
                .map(SearchResponse::getAggregations)
                .map(i -> i.get(groupBy))
                .map(i -> ((ParsedStringTerms) i).getBuckets())
                .map(this::mapToFlatList)
                .orElse(new ArrayList<>());
    }

    private List<String> mapToFlatList(List<? extends Terms.Bucket> buckets) {
        return buckets.stream().map(i -> i.getKeyAsString() + SEMICOLON.value() + i.getDocCount()).collect(toList());
    }

    private void parseHits(List<Cat> list, SearchHit[] hits) {
        for (SearchHit hit : hits) {
            Optional<Cat> cat = catMapper.convert(hit.getSourceAsString());
            cat.ifPresent(list::add);
        }
    }


    private SearchHits getHits(SearchResponse response) {
        return Optional.ofNullable(response.getHits()).orElse(SearchHits.empty());
    }

    private SearchHit[] getHits(SearchHits response) {
        return Optional.ofNullable(response.getHits()).orElse(new SearchHit[0]);
    }
}

