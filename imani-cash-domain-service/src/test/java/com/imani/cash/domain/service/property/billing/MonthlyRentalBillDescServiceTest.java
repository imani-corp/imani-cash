package com.imani.cash.domain.service.property.billing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imani.cash.domain.payment.FeePaymentChargeTypeE;
import com.imani.cash.domain.property.billing.MonthlyRentalBill;
import com.imani.cash.domain.property.billing.MonthlyRentalBillExplained;
import com.imani.cash.domain.property.billing.MonthlyRentalFee;
import com.imani.cash.domain.property.billing.RentalFeeTypeE;
import com.imani.cash.domain.property.billing.repository.IMonthlyRentalBillRepository;
import com.imani.cash.domain.property.billing.repository.IMonthlyRentalFeeRepository;
import com.imani.cash.domain.service.mock.MockObjectMapper;
import com.imani.cash.domain.service.util.DateTimeUtil;
import com.imani.cash.domain.service.util.DateTimeUtilTest;
import com.imani.cash.domain.user.repository.IUserResidenceRepository;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

/**
 * TODO complete test assertions.  Add property, apartment, floors, room details and verify.
 * @author manyce400 
 */
@RunWith(MockitoJUnitRunner.class)
public class MonthlyRentalBillDescServiceTest extends AbstractMonthlyRentalBillingTest {



    private DateTime rentalMonth;

    private MonthlyRentalBill monthlyRentalBill;
    
    @Spy
    private DateTimeUtil iDateTimeUtil;

    @Mock
    private IMonthlyRentalFeeRepository iMonthlyRentalFeeRepository;
    
    @Mock
    private IMonthlyRentalBillRepository iMonthlyRentalBillRepository;
    
    @Mock
    private IUserResidenceRepository iUserResidenceRepository;
    
    @InjectMocks
    @Spy
    private MonthlyRentalFeeService monthlyRentalFeeService;
    
    @InjectMocks
    @Spy
    private MonthlyPropertySvcChargeService monthlyPropertySvcChargeService;
    
    @InjectMocks
    private MonthlyRentalBillDescService monthlyRentalBillGenService;

    private ObjectMapper mapper = new MockObjectMapper();


    @Before
    public void before() {
        super.before();
        Mockito.when(iUserResidenceRepository.findUserResidence(Mockito.any())).thenReturn(userResidence);
        Mockito.when(iMonthlyRentalBillRepository.getUserMonthlyRentalBill(Mockito.any(), Mockito.any())).thenReturn(null);
        
        // Build mock MonthlyRentalBill
        rentalMonth = DateTime.parse("2019-09-01 00:00:00", DateTimeUtilTest.DEFAULT_FORMATTER);
        monthlyRentalBill = MonthlyRentalBill.builder()
                .userResidence(userResidence)
                .rentalAgreement(userResidence.getRentalAgreement())
                .amountPaid(0.0)
                .rentalMonth(rentalMonth)
                .build();

        // Return a Late Fee
        MonthlyRentalFee lateRentalFee = MonthlyRentalFee.builder()
                .feeName("Monthly Rent Late Fee")
                .feePaymentChargeTypeE(FeePaymentChargeTypeE.FLAT_RATE_FEE)
                .optionalFlatRate(10.00)
                .rentalFeeTypeE(RentalFeeTypeE.LATE_FEE)
                .build();
        Mockito.when(iMonthlyRentalFeeRepository.findPropertyMonthlyRentalFeeByType(Mockito.any(), Mockito.any())).thenReturn(lateRentalFee);

    }

    
    @Test
    public void testCalculateTotalAmountDue() {
        // Rental agreement has an amount of $1,800.  Property services signed up for add up to $200 so total amount due should be $2,000
        Double totalAmtDueWithFeesAndServiceCharge = monthlyRentalBillGenService.calculateTotalAmountDue(monthlyRentalBill, userResidence.getUserResidencePropertyServices());
        Assert.assertEquals(new Double(2000), totalAmtDueWithFeesAndServiceCharge);

        // Add a $20 rental late fee and verify total amount due
        MonthlyRentalFee monthlyRentalFee = MonthlyRentalFee.builder()
                .rentalFeeTypeE(RentalFeeTypeE.LATE_FEE)
                .feePaymentChargeTypeE(FeePaymentChargeTypeE.FLAT_AMOUNT_FEE)
                .optionalFlatAmount(20.00)
                .build();
        monthlyRentalBill.addMonthlyRentalFee(monthlyRentalFee);

        totalAmtDueWithFeesAndServiceCharge = monthlyRentalBillGenService.calculateTotalAmountDue(monthlyRentalBill, userResidence.getUserResidencePropertyServices());
        Assert.assertEquals(new Double(2020), totalAmtDueWithFeesAndServiceCharge);

        // Add a second $40 rental late fee and verify total amount due
        MonthlyRentalFee monthlyRentalFee2 = MonthlyRentalFee.builder()
                .rentalFeeTypeE(RentalFeeTypeE.LATE_FEE)
                .feePaymentChargeTypeE(FeePaymentChargeTypeE.FLAT_AMOUNT_FEE)
                .optionalFlatAmount(40.00)
                .build();
        monthlyRentalBill.addMonthlyRentalFee(monthlyRentalFee2);

        totalAmtDueWithFeesAndServiceCharge = monthlyRentalBillGenService.calculateTotalAmountDue(monthlyRentalBill, userResidence.getUserResidencePropertyServices());
        Assert.assertEquals(new Double(2060), totalAmtDueWithFeesAndServiceCharge);
    }
    
    @Test
    public void testGetCurrentMonthRentalBill() {
        Optional<MonthlyRentalBillExplained>  monthlyRentalBillExplained = monthlyRentalBillGenService.getCurrentMonthRentalBill(userResidence.getUserRecord());
        try {
            String value = mapper.writeValueAsString(monthlyRentalBillExplained.get());
            System.out.println("value = " + value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
