package com.imani.cash.domain.property.billing.repository;

import com.imani.cash.domain.property.billing.MonthlyRentalFee;
import com.imani.cash.domain.property.billing.RentalFeeTypeE;
import com.imani.cash.domain.property.rental.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface IMonthlyRentalFeeRepository extends JpaRepository<MonthlyRentalFee, Long> {


    @Query("Select monthlyRentalFee From MonthlyRentalFee monthlyRentalFee Where monthlyRentalFee.property = ?1 and monthlyRentalFee.rentalFeeTypeE = ?2")
    public MonthlyRentalFee findPropertyMonthlyRentalFeeByType(Property property, RentalFeeTypeE rentalFeeTypeE);

}
