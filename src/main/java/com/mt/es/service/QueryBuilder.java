package com.mt.es.service;

import com.mt.es.model.CatSearchWrapper;
import lombok.extern.apachecommons.CommonsLog;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.indices.TermsLookup;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.mt.es.config.CatConstants.ASTERISK;
import static com.mt.es.config.CatConstants.BIRTH_DATE;
import static com.mt.es.config.CatConstants.CAT;
import static com.mt.es.config.CatConstants.CATS;
import static com.mt.es.config.CatConstants.EYE_COLOUR;
import static com.mt.es.config.CatConstants.FUR_COLOUR;
import static com.mt.es.config.CatConstants.KEYWORD_WITH_DOT;
import static com.mt.es.config.CatConstants.NAME;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsLookupQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

@Service
@CommonsLog
public class QueryBuilder {

    public void findInRange(BoolQueryBuilder query, CatSearchWrapper search) {
        RangeQueryBuilder rq = rangeQuery(BIRTH_DATE.value());
        Optional.ofNullable(search.getFrom()).ifPresent(rq::from);
        Optional.ofNullable(search.getTo()).ifPresent(rq::to);
        if (Objects.nonNull(rq.from()) && Objects.nonNull(rq.to())) {
            query.filter(rq);
        }
    }
    public void findByName(BoolQueryBuilder query, List<String> names) {
        query.filter(termsQuery(NAME.value() + KEYWORD_WITH_DOT.value(), names));
    }

    public void findByChar(BoolQueryBuilder query, char c) {
        query.should(wildcardQuery(NAME.value() + KEYWORD_WITH_DOT.value(), c + ASTERISK.value()));
    }

    public void findByLookupQuery(BoolQueryBuilder query, Long id) {
        TermsLookup lookup = new TermsLookup(CATS.value(), CAT.value(), id.toString(), EYE_COLOUR.value());
        query.filter(termsLookupQuery(FUR_COLOUR.value() + KEYWORD_WITH_DOT.value(), lookup));
    }

    public AggregationBuilder groupBy(String groupBy) {
        return terms(groupBy).field(groupBy + KEYWORD_WITH_DOT.value());
    }
}

