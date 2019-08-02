package com.imani.cash.domain.zdata.automation.nyc;

import com.imani.cash.domain.service.property.rental.IPropertyBuilderService;
import com.imani.cash.domain.service.property.rental.PropertyBuilderService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author manyce400
 */
@Service(NYCPropertyItemWriter.SPRING_BEAN)
public class NYCPropertyItemWriter implements ItemWriter<NYCProperty> {


    @Autowired
    @Qualifier(PropertyBuilderService.SPRING_BEAN)
    private IPropertyBuilderService iPropertyBuilderService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NYCPropertyItemWriter.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.zdata.automation.nyc.NYCPropertyItemWriter";

    @Override
    public void write(List<? extends NYCProperty> items) throws Exception {
        LOGGER.info("Writing items to permanent store: {}", items);

        for(NYCProperty nycProperty : items) {
            iPropertyBuilderService.buildAndRecordProperty(nycProperty);
        }
    }
}
