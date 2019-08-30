package com.imani.cash.domain.zdata.automation.borough;

import com.imani.cash.domain.geographical.Borough;
import com.imani.cash.domain.geographical.BoroughIndex;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author manyce400
 */
@Configuration
@EnableBatchProcessing
public class BoroughCacheBatchConfiguration {



    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BoroughCacheBatchConfiguration.class);


    // Mock AuthenticationManager for Service beans to bootstrap
    @Bean
    public AuthenticationManager configureAuthenticationManager() {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        };
    }


    @Bean(name = "imani.batch.processing.taskExecutor")
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(30);
        return taskExecutor;
    }

    @Bean(name = "imani.batch.processing.borough.cache.step")
    public Step step1(@Qualifier(BoroughItemProcessor.SPRING_BEAN) BoroughItemProcessor boroughItemProcessor,
                      @Qualifier(BoroughItemWriter.SPRING_BEAN) BoroughItemWriter writer,
                      @Qualifier(BoroughItemReader.SPRING_BEAN) BoroughItemReader boroughItemReader,
                      @Qualifier("imani.batch.processing.taskExecutor") TaskExecutor taskExecutor) {
        return stepBuilderFactory.get("step1")
                .<Borough, BoroughIndex> chunk(2)
                .reader(boroughItemReader)
                .processor(boroughItemProcessor)
                .writer(writer)
                //.taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Job importUserJob(@Qualifier("imani.batch.processing.borough.cache.step") Step step) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                //.listener(listener)
                .flow(step)
                .end()
                .build();
    }

}
