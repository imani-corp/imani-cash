package com.imani.cash;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author manyce400
 */
@Configuration
public class RestBeansConfigurator {


    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate restTemplate;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RestBeansConfigurator.class);


    @PostConstruct
    public void enhanceObjectMapper() {
        LOGGER.info("Modifying JacksonMapper ObjectMapper default behavior with JDK8 and Joda modules....");
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}
