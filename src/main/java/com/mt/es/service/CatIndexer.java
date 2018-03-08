package com.mt.es.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.es.model.Cat;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.mt.es.config.CatConstants.CAT;
import static com.mt.es.config.CatConstants.CATS;
import static com.mt.es.config.CatConstants.PUT;
import static com.mt.es.config.CatConstants.SLASH;

@Service
@CommonsLog
public class CatIndexer {

    @Autowired
    private RestHighLevelClient restClient;

    @Autowired
    private List<Cat> cats;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IndexCreator indexCreator;

    public void createIndex() {
        StringEntity entity = new StringEntity(indexCreator.prepareCatIndex(), ContentType.APPLICATION_JSON);
        try {
            restClient.getLowLevelClient().performRequest(PUT.value(), SLASH.value() + CATS.value(), Collections.emptyMap(), entity);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }

    public void indexCats() {
        cats.forEach(i -> {
            try {
                String str = mapper.writeValueAsString(i);
                IndexRequest request = new IndexRequest(CATS.value(), CAT.value(), Optional.ofNullable(i.getId()).orElse(new Random().nextLong()).toString());
                request.source(str, XContentType.JSON);
                restClient.index(request);
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
        });
    }
}

