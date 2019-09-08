package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.contact.EmbeddedContactInfo;
import com.imani.cash.domain.property.billing.PropertyService;
import com.imani.cash.domain.property.rental.Property;
import com.imani.cash.domain.property.rental.RentalAgreement;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserResidence;
import com.imani.cash.domain.user.UserResidencePropertyService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractMonthlyRentalBillingTest {



    protected UserResidence userResidence;

    public static final Integer PROPERTY_NUM_DAYS_PAYMENT_LATE = 4;


    protected List<UserResidencePropertyService> userResidencePropertyServices;

    @Before
    public void before() {
        // Create a RentalAgreement for this test session
        RentalAgreement rentalAgreement = buildRentalAgreement();

        // Create Mock user for these tests
        UserRecord userRecord = buildUserRecord();

        // Build mock property for this test
        Property property = buildProperty();

        userResidence = UserResidence.builder()
                .userRecord(userRecord)
                .property(property)
                .rentalAgreement(rentalAgreement)
                .build();

        // Build and add PropertyServices to UserResidence
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

        userResidence.addPropertyService(propertyService1);
        userResidence.addPropertyService(propertyService2);
    }


    private Property buildProperty() {
        Property property = Property.builder()
                .mthlyNumberOfDaysPaymentLate(PROPERTY_NUM_DAYS_PAYMENT_LATE)
                .build();
        return property;
    }


    private RentalAgreement buildRentalAgreement() {
        RentalAgreement rentalAgreement = RentalAgreement.builder()
                .agreementInEffect(true)
                .monthlyRentalCost(1800.00)
                .build();
        return rentalAgreement;
    }

    private UserRecord buildUserRecord() {
        EmbeddedContactInfo embeddedContactInfo = EmbeddedContactInfo.builder()
                .email("test.user@imani.com")
                .build();

        UserRecord userRecord = UserRecord.builder()
                .firstName("Test")
                .lastName("User")
                .embeddedContactInfo(embeddedContactInfo)
                .build();
        return userRecord;
    }

}
