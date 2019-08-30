package com.imani.cash.domain.zdata.automation.borough;

import com.imani.cash.domain.geographical.BoroughIndex;
import com.imani.cash.domain.geographical.repository.IBoroughIndexSolrCrudRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
@Service(BoroughItemWriter.SPRING_BEAN)
public class BoroughItemWriter implements ItemWriter<BoroughIndex> {


    @Autowired
    private IBoroughIndexSolrCrudRepository iBoroughIndexSolrCrudRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BoroughItemWriter.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.zdata.automation.borough.BoroughItemWriter";

    @Override
    public void write(List<? extends BoroughIndex> items) throws Exception {
        for(BoroughIndex boroughIndex : items) {
            Optional<BoroughIndex> existingBoroughIndex = iBoroughIndexSolrCrudRepository.findById(boroughIndex.getId());
            if(!existingBoroughIndex.isPresent()) {
                LOGGER.info("Saving new instance of boroughIndex:=> {}", boroughIndex);
                iBoroughIndexSolrCrudRepository.save(boroughIndex);
            } else {
                LOGGER.info("Skipping persistence of boroughIndex:=> {}", boroughIndex);
            }
        }
    }

}