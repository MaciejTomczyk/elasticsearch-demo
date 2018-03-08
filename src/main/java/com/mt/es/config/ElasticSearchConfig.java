package com.mt.es.config;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {


    private static final String HTTP = "http";

    private static final String ES_HOST = "localhost";

    private static final int ES_PORT = 9200;

    private static final String ES_USERNAME = "elasticsearch";

    private static final String NOT_A_PASS = "elasticsearch";


    @Bean
    public RestHighLevelClient restHighLevelClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(ES_USERNAME, NOT_A_PASS));

        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(ES_HOST, ES_PORT, HTTP))
                        .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)));

    }
}

