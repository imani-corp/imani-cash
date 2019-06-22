package com.imani.cash.domain.service.property.rental;

import com.imani.cash.domain.property.rental.Apartment;
import com.imani.cash.domain.property.rental.RentalAgreement;
import com.imani.cash.domain.property.rental.RentalTransaction;
import com.imani.cash.domain.property.rental.RentalTransactionTypeE;
import com.imani.cash.domain.property.rental.repository.IApartmentRepository;
import com.imani.cash.domain.property.rental.repository.IRentalTransactionRepository;
import com.imani.cash.domain.user.UserRecord;
import org.joda.time.DateTime;
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
    private IApartmentRepository iApartmentRepository;


    @Autowired
    @Qualifier(RentalAgreementService.SPRING_BEAN)
    private IRentalAgreementService iRentalAgreementService;

    @Autowired
    private IRentalTransactionRepository iRentalTransactionRepository;



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PropertyRentalService.class);


    public static final String SPRING_BEAN = "com.imani.cash.domain.service.property.rental.PropertyRentalService";


    @Transactional
    @Override
    public RentalTransaction rentApartmentToTenant(RentalAgreement rentalAgreement, UserRecord tenant, Apartment apartment) {
        Assert.notNull(rentalAgreement, "RentalAgreement cannot be null");
        Assert.notNull(tenant, "UserRecord cannot be null");
        Assert.notNull(apartment, "Apartment cannot be null");
        LOGGER.debug("Renting out apartment => {} on floor => to user....", apartment.getApartmentNumber(), apartment.getFloor().getFloorNumber());



        // First verify that this apartment is not already rented
        if(!apartment.isRented()) {
            if (iRentalAgreementService.isRentalAgreementInForce(rentalAgreement)) {
                apartment.setRented(true);
                apartment.setRentedByUser(tenant);
                iApartmentRepository.save(apartment);
                RentalTransaction rentalTransaction = getRentalTransactionSuccessResult(tenant, apartment);
                iRentalTransactionRepository.save(rentalTransaction);
                return rentalTransaction;
            }
        }

        RentalTransaction rentalTransaction = getRentalTransactionApartmentAlreadyRentedResult(tenant, apartment);
        iRentalTransactionRepository.save(rentalTransaction);
        return rentalTransaction;
    }


    RentalTransaction getRentalTransactionSuccessResult(UserRecord tenant, Apartment apartment) {
        LOGGER.info("Apartment => {} on Floor => is now succesfully rented", apartment.getApartmentNumber(), apartment.getFloor().getFloorNumber());
        RentalTransaction rentalTransaction = RentalTransaction.builder()
                .transactionSuccessful(true)
                .apartment(apartment)
                .rentalTransactionTypeE(RentalTransactionTypeE.Rental)
                .transactionDate(DateTime.now())
                .userRecord(tenant)
                .build();
        return rentalTransaction;
    }

    RentalTransaction getRentalTransactionApartmentAlreadyRentedResult(UserRecord tenant, Apartment apartment) {
        LOGGER.warn("Apartment => {} on Floor => is already currently rented.  Failing transaction...", apartment.getApartmentNumber(), apartment.getFloor().getFloorNumber());
        RentalTransaction rentalTransaction = RentalTransaction.builder()
                .transactionSuccessful(false)
                .apartment(apartment)
                .transactionMessage("Apartment is currently rented")
                .rentalTransactionTypeE(RentalTransactionTypeE.Rental)
                .transactionDate(DateTime.now())
                .userRecord(tenant)
                .build();
        return rentalTransaction;
    }

}
