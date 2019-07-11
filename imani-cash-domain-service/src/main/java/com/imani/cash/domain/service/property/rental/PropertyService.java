package com.imani.cash.domain.service.property.rental;

import com.imani.cash.domain.property.rental.Property;
import com.imani.cash.domain.property.rental.repository.IPropertyRepository;
import com.imani.cash.domain.user.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service(PropertyService.SPRING_BEAN)
public class PropertyService implements IPropertyService {


    @Autowired
    private IPropertyRepository iPropertyRepository;


    public static final String SPRING_BEAN = "ccom.imani.cash.domain.service.property.rental.PropertyService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PropertyService.class);


    @Override
    public Property findUserRecordProperty(UserRecord userRecord) {
        Assert.notNull(userRecord, "UserRecord cannot be null");
        LOGGER.debug("Finding Property for UserRecord:=> {}", userRecord);
        return null;
    }

    @Override
    public void persistUserRecordProperty(UserRecord userRecord, Property property) {
        Assert.notNull(userRecord, "UserRecord cannot be null");
        Assert.notNull(property, "property cannot be null");

    }
}
