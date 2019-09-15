package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.property.billing.MonthlyRentalBill;
import com.imani.cash.domain.property.billing.MonthlyRentalBillExplained;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserResidencePropertyService;

import java.util.Optional;
import java.util.Set;

/**
 * @author manyce400
 */
public interface IMonthlyRentalBillDescService {

    public Optional<MonthlyRentalBillExplained> getCurrentMonthRentalBill(UserRecord userRecord);

    public Double calculateTotalAmountDue(MonthlyRentalBill monthlyRentalBill, Set<UserResidencePropertyService> userResidencePropertyServices);

}
