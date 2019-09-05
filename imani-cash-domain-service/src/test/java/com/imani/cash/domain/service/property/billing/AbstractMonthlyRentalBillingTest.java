package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.contact.EmbeddedContactInfo;
import com.imani.cash.domain.property.billing.PropertyService;
import com.imani.cash.domain.property.rental.RentalAgreement;
import com.imani.cash.domain.user.UserPropertyService;
import com.imani.cash.domain.user.UserRecord;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractMonthlyRentalBillingTest {


    protected UserRecord userRecord;

    protected RentalAgreement rentalAgreement;

    protected List<UserPropertyService> userPropertyServices;

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
        userPropertyServices = new ArrayList<>();
        userPropertyServices.add(userPropertyService1);
        userPropertyServices.add(userPropertyService2);

        // Create a RentalAgreement for this test session
        rentalAgreement = RentalAgreement.builder()
                .agreementInEffect(true)
                .monthlyRentalCost(1800.00)
                .build();

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

}
