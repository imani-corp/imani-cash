package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.property.billing.MonthlyRentalBill;
import com.imani.cash.domain.property.billing.PropertyServiceChargeExplained;
import com.imani.cash.domain.service.util.DateTimeUtilTest;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class MonthlyPropertySvcChargeServiceTest extends AbstractMonthlyRentalBillingTest {


    @InjectMocks
    private MonthlyPropertySvcChargeService monthlyPropertySvcChargeService;

    @Before
    public void before() {
        super.before();
    }

    @Test
    public void testApplyMonthlyPropertyServiceCharge() {
        // Payment due date
        DateTime rentalMonth = DateTime.parse("2019-09-01 00:00:00", DateTimeUtilTest.DEFAULT_FORMATTER);

        // Build mock bill
        MonthlyRentalBill monthlyRentalBill = MonthlyRentalBill.builder()
                .userResidence(userResidence)
                .rentalAgreement(userResidence.getRentalAgreement())
                .rentalMonth(rentalMonth)
                .build();

        Optional<List<PropertyServiceChargeExplained>> propertyServiceChargeExplainedList = monthlyPropertySvcChargeService.applyMonthlyPropertyServiceCharge(userResidence, monthlyRentalBill);
        System.out.println("propertyServiceChargeExplainedList = " + propertyServiceChargeExplainedList);

        // Verify property service explanation
        Assert.assertEquals(2, propertyServiceChargeExplainedList.get().size());
        Assert.assertEquals("Monthly Parking", propertyServiceChargeExplainedList.get().get(0).getServiceName());
        Assert.assertEquals(new Double(150.00), propertyServiceChargeExplainedList.get().get(0).getServiceMonthlyCost());
        Assert.assertEquals("Monthly Laundry", propertyServiceChargeExplainedList.get().get(1).getServiceName());
        Assert.assertEquals(new Double(50.00), propertyServiceChargeExplainedList.get().get(1).getServiceMonthlyCost());
    }
}
