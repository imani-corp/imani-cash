package com.imani.cash.domain.service.property.rental;

import com.imani.cash.domain.property.rental.Apartment;
import com.imani.cash.domain.property.rental.RentalAgreement;
import com.imani.cash.domain.property.rental.repository.IApartmentRepository;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.repository.IUserRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * @author manyce400
 */
@Service(PropertyRentalService.SPRING_BEAN)
public class PropertyRentalService implements IPropertyRentalService {



    @Autowired
    private IUserRecordRepository iUserRecordRepository;


    @Autowired
    private IApartmentRepository iApartmentRepository;


    @Autowired
    @Qualifier(RentalAgreementService.SPRING_BEAN)
    private IRentalAgreementService iRentalAgreementService;



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PropertyRentalService.class);


    public static final String SPRING_BEAN = "com.imani.cash.domain.service.property.rental.PropertyRentalService";


    @Transactional
    @Override
    public void rentApartmentToTenant(RentalAgreement rentalAgreement, UserRecord userRecord, Apartment apartment) {
        Assert.notNull(rentalAgreement, "RentalAgreement cannot be null");
        Assert.notNull(userRecord, "UserRecord cannot be null");
        Assert.notNull(apartment, "Apartment cannot be null");
        LOGGER.debug("Renting out apartment => {} on floor => to user....", apartment.getApartmentNumber(), apartment.getFloor().getFloorNumber());

        // First verify that this apartment is not already rented
        if(apartment.isRented()) {
            LOGGER.warn("Apartment => {} on Floor => is already rented", apartment.getApartmentNumber(), apartment.getFloor().getFloorNumber());
        } else {
            if (iRentalAgreementService.isRentalAgreementInForce(rentalAgreement)) {
                apartment.setRented(true);
                apartment.setRentedByUser(userRecord);
                iApartmentRepository.save(apartment);
            }
        }
    }



}
