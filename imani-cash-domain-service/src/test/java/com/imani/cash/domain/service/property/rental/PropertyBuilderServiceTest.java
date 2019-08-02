package com.imani.cash.domain.service.property.rental;

import com.imani.cash.domain.property.rental.Property;
import com.imani.cash.domain.service.mock.MockIHasPropertyData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertyBuilderServiceTest {


    @InjectMocks
    private PropertyBuilderService propertyBuilderService;


    @Test
    public void testInitializePropertyFloors() {
        Property property = Property.builder()
                .streetName("South West Ave")
                .propertyNumber("84848")
                .build();

        MockIHasPropertyData mockIHasPropertyData = new MockIHasPropertyData() {
            @Override
            public Integer getLegalStories() {
                return 5;
            }
        };

        propertyBuilderService.initializePropertyFloors(property, mockIHasPropertyData);
        Assert.assertEquals(5, property.getFloors().size());
    }

}
