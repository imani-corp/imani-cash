package com.imani.cash.domain.zdata.automation.nyc;

/**
 * @author manyce400
 */
//@Configuration
//@EnableBatchProcessing
public class NYCBatchConfiguration {



//    @Autowired
//    public JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    public StepBuilderFactory stepBuilderFactory;
//
//
//    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NYCBatchConfiguration.class);
//
//
//
//    @Bean(name = "CSV.FlatFileItemReader")
//    ItemReader<NYCProperty> csvFileItemReader() {
//        FlatFileItemReader<NYCProperty> csvFileReader = new FlatFileItemReader<>();
//        csvFileReader.setResource(new ClassPathResource("NYC-Building-20190630.txt"));
//        csvFileReader.setLinesToSkip(1);
//
//        LineMapper<NYCProperty> studentLineMapper = createPropertyLineMapper();
//        csvFileReader.setLineMapper(studentLineMapper);
//
//        return csvFileReader;
//    }
//
//    private LineMapper<NYCProperty> createPropertyLineMapper() {
//        DefaultLineMapper<NYCProperty> studentLineMapper = new DefaultLineMapper<>();
//
//        LineTokenizer studentLineTokenizer = createPropertyLineTokenizer();
//        studentLineMapper.setLineTokenizer(studentLineTokenizer);
//
//        FieldSetMapper<NYCProperty> studentInformationMapper = createPropertyInformationMapper();
//        studentLineMapper.setFieldSetMapper(studentInformationMapper);
//
//        return studentLineMapper;
//    }
//
//    private LineTokenizer createPropertyLineTokenizer() {
//        DelimitedLineTokenizer studentLineTokenizer = new DelimitedLineTokenizer();
//        studentLineTokenizer.setDelimiter("|");
//        studentLineTokenizer.setNames(new String[]{"BuildingID", "BoroID", "Boro", "HouseNumber", "LowHouseNumber", "HighHouseNumber", "StreetName", "Zip", "Block",
//                "Lot", "BIN", "CommunityBoard", "CensusTract", "ManagementProgram", "DoBBuildingClassID", "DoBBuildingClass", "LegalStories", "LegalClassA", "LegalClassB", "RegistrationID", "LifeCycle", "RecordStatusID", "RecordStatus"});
//        return studentLineTokenizer;
//    }
//
//    private FieldSetMapper<NYCProperty> createPropertyInformationMapper() {
//        BeanWrapperFieldSetMapper<NYCProperty> studentInformationMapper = new BeanWrapperFieldSetMapper<>();
//        studentInformationMapper.setTargetType(NYCProperty.class);
//        studentInformationMapper.setDistanceLimit(0);
//        return studentInformationMapper;
//    }
//
//
//    // Mock AuthenticationManager for Service beans to bootstrap
//    @Bean
//    public AuthenticationManager configureAuthenticationManager() {
//        return new AuthenticationManager() {
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                return null;
//            }
//        };
//    }
//
//    @Bean(name = "Property.TaskExecutor")
//    public TaskExecutor taskExecutor() {
//        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
//        taskExecutor.setConcurrencyLimit(30);
//        return taskExecutor;
//    }
//
//    @Bean(name = "CSV.Step")
//    public Step step1(@Qualifier(NYCPropertyItemProcessor.SPRING_BEAN) NYCPropertyItemProcessor nycPropertyItemProcessor,
//                      @Qualifier(NYCPropertyItemWriter.SPRING_BEAN) NYCPropertyItemWriter writer,
//                      @Qualifier("CSV.FlatFileItemReader") FlatFileItemReader<NYCProperty> flatFileItemReader,
//                      @Qualifier("Property.TaskExecutor") TaskExecutor taskExecutor) {
//        return stepBuilderFactory.get("step1")
//                .<NYCProperty, NYCProperty> chunk(10)
//                .reader(flatFileItemReader)
//                .processor(nycPropertyItemProcessor)
//                .writer(writer)
//                .taskExecutor(taskExecutor)
//                .build();
//    }
//
//    @Bean
//    public Job importUserJob(@Qualifier("CSV.Step") Step step) {
//        return jobBuilderFactory.get("importUserJob")
//                .incrementer(new RunIdIncrementer())
//                //.listener(listener)
//                .flow(step)
//                .end()
//                .build();
//    }
}
