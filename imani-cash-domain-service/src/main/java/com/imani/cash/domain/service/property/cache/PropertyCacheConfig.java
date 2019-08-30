package com.imani.cash.domain.service.property.cache;

import com.imani.cash.domain.geographical.Borough;
import com.imani.cash.domain.geographical.repository.IBoroughRepository;
import com.imani.cash.domain.property.rental.Property;
import com.imani.cash.domain.property.rental.PropertyIndex;
import com.imani.cash.domain.property.rental.repository.IPropertyIndexSolrCrudRepository;
import com.imani.cash.domain.property.rental.repository.IPropertyRepository;
import com.imani.cash.domain.service.property.rental.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class PropertyCacheConfig {


    @Autowired
    private IBoroughRepository iBoroughRepository;

    @Autowired
    private IPropertyRepository iPropertyRepository;

    @Autowired
    private IPropertyIndexSolrCrudRepository iPropertySolrCrudRepository;

    @Autowired
    @Qualifier(PropertyService.SPRING_BEAN)
    private IPropertyCacheService iPropertyCacheService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PropertyCacheConfig.class);


    @PostConstruct
    public void bootPropertyCache() {
        LOGGER.info("Booting up and loading all Imani Bronx Properties in cache.....");
        Borough borough = iBoroughRepository.getOne(2L);

        Property property = iPropertyRepository.findUniquePropertyFetchFloors(31857L);
        //System.out.println("property = " + property);
        System.out.println("\n");
        PropertyIndex propertyIndex = property.toPropertyIndex();

        System.out.println("propertyIndex = " + propertyIndex);

        System.out.println("\n");
        

//        PropertyIndex propertyIndex = PropertyIndex.builder()
//                .id(1L)
//                .propertyNumber("3311")
//                .streetName("Bainbridge Avenue")
//                .propertyManager("Patriot Management")
//                .build();
//
//        LOGGER.info("Saving new Property index.....");
        //iPropertySolrCrudRepository.save(propertyIndex);

//        LOGGER.info("Attempting to find a property by Street name...");

        //Page<PropertyIndex> page = iPropertySolrCrudRepository.findByStreetNameAndProperty("Bainbridge", PageRequest.of(0, 1));
//        iPropertySolrCrudRepository.deleteAll();
        //System.out.println("page.getTotalElements() = " + page.getTotalElements());

        //iPropertyRepository.save(property);

//        List<Property> properties = iPropertyRepository.findBoroughProperties(borough);
//
//        for(Property property : properties) {
//            iPropertyCacheService.cacheProperty(property);
//        }
    }
}
