package com.imani.cash.domain.zdata.automation.nyc;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
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
public class NYCBatchConfiguration {



    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NYCBatchConfiguration.class);


//    @Bean(name = "CSV.FlatFileItemReader")
//    public FlatFileItemReader<NYCProperty> configureReader() {
//        LOGGER.info("Configuring NYC Properties Batch file reader......");
//
//        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
//        delimitedLineTokenizer.setDelimiter("|");
//
//        return new FlatFileItemReaderBuilder<NYCProperty>()
//                .linesToSkip(1)
//                .name("personItemReader")
//                .lineTokenizer(delimitedLineTokenizer)
//                .resource(new ClassPathResource("NYC-Building-20190630.txt"))
//                .delimited()
//                .names(new String[]{"BuildingID", "BoroID", "Boro", "HouseNumber", "LowHouseNumber", "HighHouseNumber", "StreetName", "Zip", "Block",
//                        "Lot", "BIN", "CommunityBoard", "CensusTract", "ManagementProgram", "DoBBuildingClassID", "DoBBuildingClass", "LegalStories", "LegalClassA", "LegalClassB", "RegistrationID", "LifeCycle", "RecordStatusID", "RecordStatus"})
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<NYCProperty>() {
//                    {
//                        setTargetType(NYCProperty.class);
//                    }
//                })
//                .build();
//    }
//

    @Bean(name = "CSV.FlatFileItemReader")
    ItemReader<NYCProperty> csvFileItemReader() {
        FlatFileItemReader<NYCProperty> csvFileReader = new FlatFileItemReader<>();
        csvFileReader.setResource(new ClassPathResource("NYC-Building-20190630.txt"));
        csvFileReader.setLinesToSkip(1);

        LineMapper<NYCProperty> studentLineMapper = createPropertyLineMapper();
        csvFileReader.setLineMapper(studentLineMapper);

        return csvFileReader;
    }

    private LineMapper<NYCProperty> createPropertyLineMapper() {
        DefaultLineMapper<NYCProperty> studentLineMapper = new DefaultLineMapper<>();

        LineTokenizer studentLineTokenizer = createPropertyLineTokenizer();
        studentLineMapper.setLineTokenizer(studentLineTokenizer);

        FieldSetMapper<NYCProperty> studentInformationMapper = createPropertyInformationMapper();
        studentLineMapper.setFieldSetMapper(studentInformationMapper);

        return studentLineMapper;
    }

    private LineTokenizer createPropertyLineTokenizer() {
        DelimitedLineTokenizer studentLineTokenizer = new DelimitedLineTokenizer();
        studentLineTokenizer.setDelimiter("|");
        studentLineTokenizer.setNames(new String[]{"BuildingID", "BoroID", "Boro", "HouseNumber", "LowHouseNumber", "HighHouseNumber", "StreetName", "Zip", "Block",
                "Lot", "BIN", "CommunityBoard", "CensusTract", "ManagementProgram", "DoBBuildingClassID", "DoBBuildingClass", "LegalStories", "LegalClassA", "LegalClassB", "RegistrationID", "LifeCycle", "RecordStatusID", "RecordStatus"});
        return studentLineTokenizer;
    }

    private FieldSetMapper<NYCProperty> createPropertyInformationMapper() {
        BeanWrapperFieldSetMapper<NYCProperty> studentInformationMapper = new BeanWrapperFieldSetMapper<>();
        studentInformationMapper.setTargetType(NYCProperty.class);
        studentInformationMapper.setDistanceLimit(0);
        return studentInformationMapper;
    }








//    @Bean(name = "CSV.NYCPropertyItemProcessor")
//    public NYCPropertyItemProcessor configureProcessor() {
//        return new NYCPropertyItemProcessor();
//    }
//
//    @Bean(name = "CSV.NYCPropertyItemWriter")
//    public NYCPropertyItemWriter configureNYCPropertyItemWriter() {
//        return new NYCPropertyItemWriter();
//    }

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

    @Bean(name = "Property.TaskExecutor")
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(30);
        return taskExecutor;
    }

    @Bean(name = "CSV.Step")
    public Step step1(@Qualifier(NYCPropertyItemProcessor.SPRING_BEAN) NYCPropertyItemProcessor nycPropertyItemProcessor,
                      @Qualifier(NYCPropertyItemWriter.SPRING_BEAN) NYCPropertyItemWriter writer,
                      @Qualifier("CSV.FlatFileItemReader") FlatFileItemReader<NYCProperty> flatFileItemReader,
                      @Qualifier("Property.TaskExecutor") TaskExecutor taskExecutor) {
        return stepBuilderFactory.get("step1")
                .<NYCProperty, NYCProperty> chunk(10)
                .reader(flatFileItemReader)
                .processor(nycPropertyItemProcessor)
                .writer(writer)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Job importUserJob(@Qualifier("CSV.Step") Step step) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                //.listener(listener)
                .flow(step)
                .end()
                .build();
    }
}
