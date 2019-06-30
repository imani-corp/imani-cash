package com.imani.cash;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Autoconfiguration to modify default behavior of Jackson JSON API.
 *
 * @author manyce400
 */
@Configuration
class JSONConfiguration {


    @Autowired
    private ObjectMapper mapper;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JSONConfiguration.class);


    @PostConstruct
    public void enhanceObjectMapper() {
        LOGGER.info("Modifying JacksonMapper ObjectMapper default behavior....");
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


}
