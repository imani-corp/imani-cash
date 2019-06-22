package com.imani.cash.domain.service.property.rental;

import com.imani.cash.domain.property.rental.*;
import com.imani.cash.domain.property.rental.repository.IApartmentRepository;
import com.imani.cash.domain.property.rental.repository.IRentalTransactionRepository;
import com.imani.cash.domain.user.UserRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertyRentalServiceTest {


    @Mock
    private IApartmentRepository iApartmentRepository;

    @Mock
    private IRentalAgreementService iRentalAgreementService;

    @Mock
    private IRentalTransactionRepository iRentalTransactionRepository;

    @InjectMocks
    private PropertyRentalService propertyRentalService;


    @Test
    public void testGetApartmentRentalSuccessResult() {
        Apartment apartment = Apartment.builder()
                .apartmentNumber("2E")
                .isRented(false)
                .floor(new Floor())
                .build();
        RentalTransaction rentalTransaction = propertyRentalService.getRentalTransactionSuccessResult(new UserRecord(), apartment);
        Assert.assertTrue(rentalTransaction.isTransactionSuccessful());
        Assert.assertEquals(RentalTransactionTypeE.Rental, rentalTransaction.getRentalTransactionTypeE());
    }

    @Test
    public void testGetApartmentAlreadyRentedResult() {
        Apartment apartment = Apartment.builder()
                .apartmentNumber("2E")
                .isRented(true)
                .floor(new Floor())
                .build();
        RentalTransaction rentalTransaction = propertyRentalService.getRentalTransactionApartmentAlreadyRentedResult(new UserRecord(), apartment);
        Assert.assertFalse(rentalTransaction.isTransactionSuccessful());
        Assert.assertEquals(RentalTransactionTypeE.Rental, rentalTransaction.getRentalTransactionTypeE());
        Assert.assertEquals("Apartment is currently rented", rentalTransaction.getTransactionMessage());
    }

    @Test
    public void testRentApartmentToTenantSuccess() {
        // Simulate a all calls to iRentalAgreementService returning true to signify agreement is in force
        Mockito.when(iRentalAgreementService.isRentalAgreementInForce(Mockito.any())).thenReturn(Boolean.TRUE);

        // Simulate an apartment that is not already rented.
        Apartment apartment = Apartment.builder()
                .apartmentNumber("2E")
                .isRented(false)
                .floor(new Floor())
                .build();

        RentalTransaction rentalTransaction = propertyRentalService.rentApartmentToTenant(new RentalAgreement(), new UserRecord(), apartment);
        Assert.assertTrue(rentalTransaction.isTransactionSuccessful());
    }

    @Test
    public void testRentApartmentToTenantWhenApartmentAlreadyRented() {
        // Simulate a all calls to iRentalAgreementService returning true to signify agreement is in force
        Mockito.when(iRentalAgreementService.isRentalAgreementInForce(Mockito.any())).thenReturn(Boolean.TRUE);

        // Simulate an apartment that is already rented.
        Apartment apartment = Apartment.builder()
                .apartmentNumber("2E")
                .isRented(true)
                .floor(new Floor())
                .build();

        RentalTransaction rentalTransaction = propertyRentalService.rentApartmentToTenant(new RentalAgreement(), new UserRecord(), apartment);
        Assert.assertFalse(rentalTransaction.isTransactionSuccessful());
    }
}
