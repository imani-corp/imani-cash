package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.contact.EmbeddedContactInfo;
import com.imani.cash.domain.property.billing.PropertyService;
import com.imani.cash.domain.property.rental.*;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserResidence;
import com.imani.cash.domain.user.UserResidencePropertyService;
import org.joda.time.DateTime;
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

        // Build actual apartment that the user resides in.
        Apartment apartment = buildApartment();

        userResidence = UserResidence.builder()
                .userRecord(userRecord)
                .property(property)
                .rentalAgreement(rentalAgreement)
                .primaryResidence(true)
                .apartment(apartment)
                .build();

        // Build and add PropertyServices to UserResidence
        PropertyService propertyService1 = PropertyService.builder()
                .serviceName("Monthly Parking")
                .serviceMonthlyCost(150.00)
                .serviceActive(true)
                .createDate(DateTime.now())
                .build();

        PropertyService propertyService2 = PropertyService.builder()
                .serviceName("Monthly Laundry")
                .serviceMonthlyCost(50.00)
                .serviceActive(true)
                .createDate(DateTime.now())
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

    private Apartment buildApartment() {
        Floor floor = Floor.builder()
                .floorNumber(2)
                .build();

        // Build 2 bedrooms for this apartment
        Bedroom master = Bedroom.builder()
                .isMasterBedroom(true)
                .squareFootage(450L)
                .build();

        Bedroom secondBedroom = Bedroom.builder()
                .isMasterBedroom(true)
                .squareFootage(250L)
                .build();

        Apartment apartment = Apartment.builder()
                .isRented(true)
                .apartmentNumber("2 - P15")
                .floor(floor)
                .bedroom(master)
                .bedroom(secondBedroom)
                .build();
        return apartment;
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
