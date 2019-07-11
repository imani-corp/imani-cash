package com.imani.cash.domain.service.property.rental;

import com.imani.cash.domain.property.rental.Property;
import com.imani.cash.domain.user.UserRecord;

/**
 * @author manyce400
 */
public interface IPropertyService {

    public Property findUserRecordProperty(UserRecord userRecord);

    public void persistUserRecordProperty(UserRecord userRecord, Property property);

}
