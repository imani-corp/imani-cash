package com.imani.cash;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

/**
 * @author manyce400
 */
@Configuration
@EnableSolrRepositories(
        basePackages = "com.imani.cash.domain",
        namedQueriesLocation = "classpath:solr-named-queries.properties")
@ComponentScan
public class SolrConfiguration {


    @Bean
    public SolrClient solrClient() {
        HttpSolrClient.Builder builder = new HttpSolrClient.Builder();
        builder.withBaseSolrUrl("http://localhost:8983/solr");
        return builder.build();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client) throws Exception {
        return new SolrTemplate(client);
    }

}
