package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.property.billing.MonthlyRentalBill;
import com.imani.cash.domain.user.UserRecord;

import java.util.Optional;

/**
 * @author manyce400
 */
public interface IMonthlyRentalBillGenService {


    public Optional<MonthlyRentalBill> generateMthlyBill(UserRecord userRecord);


    public Optional<Double> calculateMthlyCostOfOptionalServices(UserRecord userRecord);

}
