package com.imani.cash.domain.service.property.billing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imani.cash.domain.payment.FeePaymentChargeTypeE;
import com.imani.cash.domain.property.billing.MonthlyRentalBillExplained;
import com.imani.cash.domain.property.billing.MonthlyRentalFee;
import com.imani.cash.domain.property.billing.RentalFeeTypeE;
import com.imani.cash.domain.property.billing.repository.IMonthlyRentalBillRepository;
import com.imani.cash.domain.property.billing.repository.IMonthlyRentalFeeRepository;
import com.imani.cash.domain.service.mock.MockObjectMapper;
import com.imani.cash.domain.service.util.DateTimeUtil;
import com.imani.cash.domain.user.repository.IUserResidenceRepository;
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
 * @author manyce400 
 */
@RunWith(MockitoJUnitRunner.class)
public class MonthlyRentalBillDescServiceTest extends AbstractMonthlyRentalBillingTest {
    
    
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
//        monthlyRentalFeeService = Mockito.spy(new MonthlyRentalFeeService());
//        monthlyPropertySvcChargeService = Mockito.spy(new MonthlyPropertySvcChargeService());
//        MockitoAnnotations.initMocks(this);
        Mockito.when(iUserResidenceRepository.findUserResidence(Mockito.any())).thenReturn(userResidence);
        Mockito.when(iMonthlyRentalBillRepository.getUserMonthlyRentalBill(Mockito.any(), Mockito.any())).thenReturn(null);

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
    public void testGetCurrentMonthRentalBill() {
        Optional<MonthlyRentalBillExplained>  monthlyRentalBillExplained = monthlyRentalBillGenService.getCurrentMonthRentalBill(userResidence.getUserRecord());
        try {
            String value = mapper.writeValueAsString(monthlyRentalBillExplained.get());
            System.out.println("value = " + value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    
    
//    @Test
//    public void testCalculateTotalMthlyCharge() {
//        Double mthlyRentalCost = 1800.00;
//        Optional<Double> totalMthlyServiceCharge = Optional.of(150.00);
//
//        double result = monthlyRentalBillGenService.calculateTotalMthlyCharge(mthlyRentalCost, totalMthlyServiceCharge);
//        Assert.assertEquals(Double.valueOf(1950.00), Double.valueOf(result));
//
//        // simulate with no additional monthly services charge
//        totalMthlyServiceCharge = Optional.empty();
//        result = monthlyRentalBillGenService.calculateTotalMthlyCharge(mthlyRentalCost, totalMthlyServiceCharge);
//        Assert.assertEquals(Double.valueOf(1800.00), Double.valueOf(result));
//    }
//
//    @Test
//    public void testCalculateMthlyCostOfOptionalServices() {
//        // We expect a total of 200 for the 2 additional charges defined in before()
//        Optional<Double> optionalServicesCharge = monthlyRentalBillGenService.calculateMthlyCostOfOptionalServices(userRecord);
//        Assert.assertEquals(Double.valueOf(200.00), optionalServicesCharge.get());
//    }
    
    
//    @Test
//    public void testGenerateMthlyBill() {
//        // Simulate no current monthly bill has already been created for user
//        Mockito.when(iMonthlyRentalBillRepository.getUserMonthlyRentalBill(Mockito.any(), Mockito.any())).thenReturn(null);
//
//        // Monthly rent = 800 Total cost of property services = 200 so we expect the total monthly rental bill to be $2,000.00
//        Optional<MonthlyRentalBill> monthlyRentalBill =  monthlyRentalBillGenService.generateMthlyBill(userRecord);
//        Assert.assertEquals(Double.valueOf(2000.00), monthlyRentalBill.get().getTotalMonthlyCharge());
//        Assert.assertEquals(Double.valueOf(1800.00), monthlyRentalBill.get().getRentalAmountCharge());
//        Assert.assertEquals(Double.valueOf(200.00), monthlyRentalBill.get().getAdditionalServicesCharge());
//        Assert.assertEquals(Double.valueOf(0.00), monthlyRentalBill.get().getUnpaidAmountCharge());
//        Assert.assertEquals(Double.valueOf(0.00), monthlyRentalBill.get().getPaidAmount());
//        Assert.assertFalse(monthlyRentalBill.get().isBillClosed());
//
//        // Verify that it created the Rental DateTime using the starting day of the current month.
//        DateTime now = DateTime.now();
//        DateTime rentalMonth = monthlyRentalBill.get().getRentalMonth();
//        Assert.assertEquals(now.getMonthOfYear(), rentalMonth.getMonthOfYear());
//        Assert.assertEquals(now.getYear(), rentalMonth.getYear());
//        Assert.assertEquals(1, rentalMonth.getDayOfMonth());
//
//        try {
//            String value = mapper.writeValueAsString(monthlyRentalBill.get());
//            System.out.println("value = " + value);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
}
