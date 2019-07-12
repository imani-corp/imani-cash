package com.imani.cash.domain.service.user;

import com.imani.cash.domain.property.rental.Apartment;
import com.imani.cash.domain.property.rental.Property;
import com.imani.cash.domain.property.rental.RentalAgreement;
import com.imani.cash.domain.user.UserRecord;

/**
 * @author manyce400
 */
public interface IUserResidenceService {


    // Creates UserResidence for a User with the expectation that this Property is a Single Family property.
    public void buildUserResidence(UserRecord userRecord, Property property, boolean primaryResidence);

    // Creates UserResidence for a User with the expectation that this Property is a Single Family property with a rental agreement
    public void buildUserResidence(UserRecord userRecord, Property property, RentalAgreement rentalAgreement, boolean primaryResidence);

    // Creates UserResidence for a User with the expectation that this Property is a Multi Family property.
    public void buildUserResidence(UserRecord userRecord, Property property, Apartment apartment, boolean primaryResidence);

    // Creates UserResidence for a User with the expectation that this Property is a Multi Family property with a rental agreement
    public void buildUserResidence(UserRecord userRecord, Property property, Apartment apartment, RentalAgreement rentalAgreement, boolean primaryResidence);

}
