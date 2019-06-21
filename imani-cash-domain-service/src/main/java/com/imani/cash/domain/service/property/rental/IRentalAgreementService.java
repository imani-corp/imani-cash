package com.imani.cash.domain.service.property.rental;

import com.imani.cash.domain.property.rental.RentalAgreement;

/**
 * @author manyce400
 */
public interface IRentalAgreementService {


    public boolean isRentalAgreementInForce(RentalAgreement rentalAgreement);
}
