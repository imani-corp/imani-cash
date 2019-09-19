package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.property.billing.MonthlyRentalBillExplained;
import com.imani.cash.domain.property.billing.RentalBillPayResult;

/**
 * @author manyce400
 */
public interface IMonthlyRentalBillPayService {

    // Execute monthly rental payment
    public RentalBillPayResult payMonthlyRental(MonthlyRentalBillExplained monthlyRentalBillExplained);

}
