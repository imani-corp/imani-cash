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
 * TODO complete test assertions.  Add property, apartment, floors, room details and verify.
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
    

}
