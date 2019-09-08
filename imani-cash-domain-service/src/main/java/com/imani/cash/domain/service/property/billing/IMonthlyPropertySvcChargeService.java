package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.property.billing.MonthlyRentalBill;
import com.imani.cash.domain.property.billing.PropertyServiceChargeExplained;
import com.imani.cash.domain.user.UserResidence;

import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
public interface IMonthlyPropertySvcChargeService {

    public Optional<List<PropertyServiceChargeExplained>> applyMonthlyPropertyServiceCharge(UserResidence userResidence, MonthlyRentalBill monthlyRentalBill);

}
