package com.imani.cash.domain.security.payment;

import com.imani.cash.domain.payment.EmbeddedPayment;
import com.imani.cash.domain.payment.RentalPaymentHistory;
import com.imani.cash.domain.payment.repository.IRentalPaymentHistoryRepository;
import com.imani.cash.domain.user.UserRecord;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class RentalPaymentHistorySecurityServiceTest {


    @Mock
    private IRentalPaymentHistoryRepository iRentalPaymentHistoryRepository;

    @InjectMocks
    private RentalPaymentHistorySecurityService rentalPaymentHistorySecurityService;

    
    @Test
    public void testValidatePayment() {
        List<RentalPaymentHistory> results = new ArrayList<>();
        Mockito.when(iRentalPaymentHistoryRepository.findUserRentalPaymentHistoryByStatusAndDate(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(results);

        UserRecord userRecord = new UserRecord();
        EmbeddedPayment embeddedPayment = new EmbeddedPayment();
        embeddedPayment.setPaymentDate(DateTime.now());
        
        // Test validating payment
        PaymentSecurityValidation paymentSecurityValidation = rentalPaymentHistorySecurityService.validatePayment(userRecord, embeddedPayment);
        Assert.assertEquals(PaymentSecurityValidation.NONE, paymentSecurityValidation);
    }

    @Test
    public void testGetPaymentDateTimeAtStartOfMonth() {
        DateTime now = DateTime.now();
        System.out.println("now = " + now);
        
        DateTime startOfMonth = rentalPaymentHistorySecurityService.getPaymentDateTimeAtStartOfMonth(now);
        System.out.println("startOfMonth = " + startOfMonth);
    }
}