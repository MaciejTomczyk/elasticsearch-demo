package com.mt.es.rest;


import com.mt.es.model.Cat;
import com.mt.es.model.CatSearchWrapper;
import com.mt.es.service.CatIndexer;
import com.mt.es.service.CatSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class CatController {

    @Autowired
    private CatIndexer catIndexer;

    @Autowired
    private CatSearch catSearch;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createIndex() {
        catIndexer.createIndex();
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ResponseEntity indexCats() {
        catIndexer.indexCats();
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<List<Cat>> findCats(@RequestBody CatSearchWrapper search) {
        List<Cat> cats = catSearch.findBySearchFilter(search);
        return ResponseEntity.ok().body(cats);
    }

    @RequestMapping(value = "/agg", method = RequestMethod.POST)
    public ResponseEntity<List<String>> aggregateCats(@RequestBody CatSearchWrapper search) {
        List<String> cats = catSearch.aggregateByField(search);
        return ResponseEntity.ok().body(cats);
    }
}

