package com.imani.cash.domain.zdata.automation.borough;

import com.imani.cash.domain.geographical.Borough;
import com.imani.cash.domain.service.geographical.BoroughService;
import com.imani.cash.domain.service.geographical.IBoroughService;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service(BoroughItemReader.SPRING_BEAN)
public class BoroughItemReader implements ItemReader<Borough> {


    private int nextBoroughIndex;

    @Autowired
    @Qualifier(BoroughService.SPRING_BEAN)
    private IBoroughService iBoroughService;

    private List<Borough> boroughList;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BoroughItemReader.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.zdata.automation.borough.BoroughItemReader";


    @Override
    public Borough read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Borough nextBorough = null;

        if(nextBoroughIndex < boroughList.size()) {
            nextBorough = boroughList.get(nextBoroughIndex);
            nextBoroughIndex++;
        }

        LOGGER.info("Current nextBoroughIndex: {} returning nextBorough: {}", nextBoroughIndex, nextBorough);
        return nextBorough;
    }

    @PostConstruct
    void initializeBoroughItemReader() {
        nextBoroughIndex = 0;
        boroughList = iBoroughService.findAll();
    }
}
