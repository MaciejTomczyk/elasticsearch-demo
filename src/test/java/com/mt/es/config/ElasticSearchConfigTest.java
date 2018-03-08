package com.mt.es.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ElasticSearchConfigTest {

    @Test
    public void testElasticSearchConfiguration() {
        // given
        ElasticSearchConfig config = new ElasticSearchConfig();

        // when
        RestHighLevelClient client = config.restHighLevelClient();

        // then
        assertNotNull(client);
    }
}
