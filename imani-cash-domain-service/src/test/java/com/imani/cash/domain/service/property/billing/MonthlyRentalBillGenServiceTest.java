package com.imani.cash.domain.service.property.billing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imani.cash.domain.contact.EmbeddedContactInfo;
import com.imani.cash.domain.property.billing.MonthlyRentalBill;
import com.imani.cash.domain.property.billing.PropertyService;
import com.imani.cash.domain.property.billing.repository.IMonthlyRentalBillRepository;
import com.imani.cash.domain.property.rental.RentalAgreement;
import com.imani.cash.domain.property.rental.repository.IRentalAgreementRepository;
import com.imani.cash.domain.service.mock.MockObjectMapper;
import com.imani.cash.domain.service.util.DateTimeUtil;
import com.imani.cash.domain.user.UserPropertyService;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.repository.IUserPropertyServiceRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author manyce400 
 */
@RunWith(MockitoJUnitRunner.class)
public class MonthlyRentalBillGenServiceTest {
    
    
    @Spy
    private DateTimeUtil iDateTimeUtil;
    
    @Mock
    private IMonthlyRentalBillRepository iMonthlyRentalBillRepository;
    
    @Mock
    private IUserPropertyServiceRepository iUserPropertyServiceRepository;
    
    @Mock
    private IRentalAgreementRepository iRentalAgreementRepository;
    
    @InjectMocks
    private MonthlyRentalBillGenService monthlyRentalBillGenService;

    private UserRecord userRecord;

    private ObjectMapper mapper = new MockObjectMapper();


    @Before
    public void before() {
        // Create mock PropertyService
        PropertyService propertyService1 = PropertyService.builder()
                .serviceName("Monthly Parking")
                .serviceMonthlyCost(150.00)
                .serviceActive(true)
                .build();

        PropertyService propertyService2 = PropertyService.builder()
                .serviceName("Monthly Laundry")
                .serviceMonthlyCost(50.00)
                .serviceActive(true)
                .build();
        
        // Create mock UserPropertyService
        UserPropertyService userPropertyService1 = UserPropertyService.builder()
                .propertyService(propertyService1)
                .active(true)
                .build();

        UserPropertyService userPropertyService2 = UserPropertyService.builder()
                .propertyService(propertyService2)
                .active(true)
                .build();

        // mockout call to retrieve additional property services that the user has signed up for
        List<UserPropertyService> userPropertyServices = new ArrayList<>();//ImmutableList.of(userPropertyService1, userPropertyService2);
        userPropertyServices.add(userPropertyService1);
        userPropertyServices.add(userPropertyService2);
        Mockito.when(iUserPropertyServiceRepository.findAllActiveUserPropertyService(Mockito.any())).thenReturn(userPropertyServices);
        
        // Create a RentalAgreement for this test session
        RentalAgreement rentalAgreement = RentalAgreement.builder()
                .agreementInEffect(true)
                .monthlyRentalCost(1800.00)
                .build();
        
        Mockito.when(iRentalAgreementRepository.findActiveUserRentalAgreement(Mockito.any())).thenReturn(rentalAgreement);

        // Create Mock user for these tests
        EmbeddedContactInfo embeddedContactInfo = EmbeddedContactInfo.builder()
                .email("test.user@imani.com")
                .build();

        userRecord = UserRecord.builder()
                .firstName("Test")
                .lastName("User")
                .embeddedContactInfo(embeddedContactInfo)
                .addUserPropertyServices(userPropertyServices)
                .build();
    }
    
    
    @Test
    public void testCalculateTotalMthlyCharge() {
        Double mthlyRentalCost = 1800.00;
        Optional<Double> totalMthlyServiceCharge = Optional.of(150.00);

        double result = monthlyRentalBillGenService.calculateTotalMthlyCharge(mthlyRentalCost, totalMthlyServiceCharge);
        Assert.assertEquals(Double.valueOf(1950.00), Double.valueOf(result));

        // simulate with no additional monthly services charge
        totalMthlyServiceCharge = Optional.empty();
        result = monthlyRentalBillGenService.calculateTotalMthlyCharge(mthlyRentalCost, totalMthlyServiceCharge);
        Assert.assertEquals(Double.valueOf(1800.00), Double.valueOf(result));
    }

    @Test
    public void testCalculateMthlyCostOfOptionalServices() {
        // We expect a total of 200 for the 2 additional charges defined in before()
        Optional<Double> optionalServicesCharge = monthlyRentalBillGenService.calculateMthlyCostOfOptionalServices(userRecord);
        Assert.assertEquals(Double.valueOf(200.00), optionalServicesCharge.get());
    }
    
    
    @Test
    public void testgenerateMthlyBill() {
        // Simulate no current monthly bill has already been created for user
        Mockito.when(iMonthlyRentalBillRepository.getUserMonthlyRentalBill(Mockito.any(), Mockito.any())).thenReturn(null);

        // Monthly rent = 800 Total cost of property services = 200 so we expect the total monthly rental bill to be $2,000.00
        Optional<MonthlyRentalBill> monthlyRentalBill =  monthlyRentalBillGenService.generateMthlyBill(userRecord);
        Assert.assertEquals(Double.valueOf(2000.00), monthlyRentalBill.get().getTotalMonthlyCharge());
        Assert.assertEquals(Double.valueOf(1800.00), monthlyRentalBill.get().getRentalAmountCharge());
        Assert.assertEquals(Double.valueOf(200.00), monthlyRentalBill.get().getAdditionalServicesCharge());
        Assert.assertEquals(Double.valueOf(0.00), monthlyRentalBill.get().getUnpaidAmountCharge());
        Assert.assertEquals(Double.valueOf(0.00), monthlyRentalBill.get().getPaidAmount());
        Assert.assertFalse(monthlyRentalBill.get().isBillClosed());

        // Verify that it created the Rental DateTime using the starting day of the current month.
        DateTime now = DateTime.now();
        DateTime rentalMonth = monthlyRentalBill.get().getRentalMonth();
        Assert.assertEquals(now.getMonthOfYear(), rentalMonth.getMonthOfYear());
        Assert.assertEquals(now.getYear(), rentalMonth.getYear());
        Assert.assertEquals(1, rentalMonth.getDayOfMonth());

        try {
            String value = mapper.writeValueAsString(monthlyRentalBill.get());
            System.out.println("value = " + value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
