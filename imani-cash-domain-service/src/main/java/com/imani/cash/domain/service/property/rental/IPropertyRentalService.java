package com.imani.cash.domain.service.property.rental;

import com.imani.cash.domain.property.rental.Apartment;
import com.imani.cash.domain.property.rental.RentalAgreement;
import com.imani.cash.domain.property.rental.RentalTransaction;
import com.imani.cash.domain.user.UserRecord;

/**
 * @author manyce400
 */
public interface IPropertyRentalService {


    /**
     * Complete the rental process and actually record the rental of Apartment to the UserRecord passed as argument.
     *
     * @param tenant
     * @param apartment
     */
    public RentalTransaction rentApartmentToTenant(RentalAgreement rentalAgreement, UserRecord tenant, Apartment apartment);

}
