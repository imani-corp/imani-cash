package com.imani.cash.domain.zdata.automation.nyc;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

/**
 * @author manyce400
 */
@Service(NYCPropertyItemProcessor.SPRING_BEAN)
public class NYCPropertyItemProcessor implements ItemProcessor<NYCProperty, NYCProperty> {



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NYCPropertyItemProcessor.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.zdata.automation.nyc.NYCPropertyItemProcessor";


    @Override
    public NYCProperty process(NYCProperty nycProperty) throws Exception {
        LOGGER.info("Processing and returning NYCProperty => {}", nycProperty);
        return nycProperty;
    }
}
