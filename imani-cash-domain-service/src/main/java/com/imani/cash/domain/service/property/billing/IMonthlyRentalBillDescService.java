package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.property.billing.MonthlyRentalBillExplained;
import com.imani.cash.domain.user.UserRecord;

import java.util.Optional;

/**
 * @author manyce400
 */
public interface IMonthlyRentalBillDescService {

    public Optional<MonthlyRentalBillExplained> getCurrentMonthRentalBill(UserRecord userRecord);

}
