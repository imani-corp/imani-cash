package com.imani.cash.domain.zdata.automation.borough;

import com.imani.cash.domain.geographical.Borough;
import com.imani.cash.domain.geographical.BoroughIndex;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service(BoroughItemProcessor.SPRING_BEAN)
public class BoroughItemProcessor implements ItemProcessor<Borough, BoroughIndex> {



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BoroughItemProcessor.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.zdata.automation.borough.BoroughItemProcessor";

    @Override
    public BoroughIndex process(Borough borough) throws Exception {
        LOGGER.info("Processing Borough:=> {)", borough);
        BoroughIndex boroughIndex = borough.toBoroughIndex();
        return boroughIndex;
    }
}
