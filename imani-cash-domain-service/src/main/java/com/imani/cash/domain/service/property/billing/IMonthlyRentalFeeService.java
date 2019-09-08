package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.property.billing.MonthlyRentalBill;
import com.imani.cash.domain.property.billing.MonthlyRentalFeeExplained;
import com.imani.cash.domain.user.UserResidence;

import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
public interface IMonthlyRentalFeeService {

    // Applies rental fee's where applicable to a MonthlyRentalBill
    public Optional<List<MonthlyRentalFeeExplained>> applyMonthlyRentalFees(UserResidence userResidence, MonthlyRentalBill monthlyRentalBill);

}
