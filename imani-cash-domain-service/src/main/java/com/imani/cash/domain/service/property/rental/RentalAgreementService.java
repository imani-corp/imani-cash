package com.imani.cash.domain.service.property.rental;

import com.imani.cash.domain.property.rental.RentalAgreement;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service(RentalAgreementService.SPRING_BEAN)
public class RentalAgreementService implements IRentalAgreementService {




    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RentalAgreementService.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.service.property.rental.RentalAgreementService";



    @Override
    public boolean isRentalAgreementInForce(RentalAgreement rentalAgreement) {
        Assert.notNull(rentalAgreement, "RentalAgreement cannot be null");
        LOGGER.debug("Checking RentalAgreement to see if its still in force => {}", rentalAgreement);

        if(rentalAgreement.getEffectiveDate() != null) {
            boolean partiesAcceptedAgreement = partiesAcceptedAgreement(rentalAgreement);
            boolean partiesAcceptanceDatesRecorded = partiesAcceptanceDatesRecorded(rentalAgreement);

            if(partiesAcceptedAgreement && partiesAcceptanceDatesRecorded) {
                return true;
            }
        }

        return false;
    }


    boolean partiesAcceptedAgreement(RentalAgreement rentalAgreement) {
        return rentalAgreement.isPropertyManagerAcceptedAgreement() && rentalAgreement.isTenantAcceptedAgreement();
    }

    boolean partiesAcceptanceDatesRecorded(RentalAgreement rentalAgreement) {
        return rentalAgreement.getPropertyManagerAcceptanceDate() != null && rentalAgreement.getTenantAcceptanceDate() != null;
    }
}
