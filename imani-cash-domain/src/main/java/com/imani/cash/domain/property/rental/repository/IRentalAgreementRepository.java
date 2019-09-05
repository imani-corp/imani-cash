package com.imani.cash.domain.property.rental.repository;

import com.imani.cash.domain.property.rental.RentalAgreement;
import com.imani.cash.domain.user.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface IRentalAgreementRepository extends JpaRepository<RentalAgreement, Long> {

    @Query("Select rentalAgreement From RentalAgreement rentalAgreement Where rentalAgreement.userRecord = ?1")
    public RentalAgreement findUserRentalAgreement(UserRecord userRecord);

    @Query("Select rentalAgreement From RentalAgreement rentalAgreement Where rentalAgreement.userRecord = ?1 and rentalAgreement.agreementInEffect = true")
    public RentalAgreement findActiveUserRentalAgreement(UserRecord userRecord);

}
