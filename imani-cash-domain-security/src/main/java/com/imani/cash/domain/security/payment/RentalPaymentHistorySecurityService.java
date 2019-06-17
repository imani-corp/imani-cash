package com.imani.cash.domain.security.payment;

import com.imani.cash.domain.payment.EmbeddedPayment;
import com.imani.cash.domain.payment.PaymentStatusE;
import com.imani.cash.domain.payment.RentalPaymentHistory;
import com.imani.cash.domain.payment.repository.IRentalPaymentHistoryRepository;
import com.imani.cash.domain.user.UserRecord;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author manyce400
 */
@Service(RentalPaymentHistorySecurityService.SPRING_BEAN)
public class RentalPaymentHistorySecurityService implements IRentalPaymentHistorySecurityService {


    @Autowired
    private IRentalPaymentHistoryRepository iRentalPaymentHistoryRepository;

    public static final String SPRING_BEAN = "com.imani.cash.domain.security.payment.RentalPaymentHistorySecurityService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RentalPaymentHistorySecurityService.class);


    @Override
    public PaymentSecurityValidation validatePayment(UserRecord userRecord, EmbeddedPayment embeddedPayment) {
        Assert.notNull(userRecord, "userRecord cannot be null");
        Assert.notNull(embeddedPayment, "embeddedPayment cannot be null");

        //LOGGER.info("Validating rent payment for user: {}", userRecord.getEmbeddedContactInfo().getEmail());

        // Find all Payments made for the payment date month at the beginning of the month.
        DateTime paymentDateTimeAtStartOfMonth = getPaymentDateTimeAtStartOfMonth(embeddedPayment.getPaymentDate());
        List<RentalPaymentHistory> rentalPaymentHistoryList = iRentalPaymentHistoryRepository.findUserRentalPaymentHistoryByStatusAndDate(userRecord, PaymentStatusE.Success, paymentDateTimeAtStartOfMonth);

        // Allow User to only make 1 rental payment per month
        boolean paymentMade = !CollectionUtils.isEmpty(rentalPaymentHistoryList);
        if(paymentMade) {
            LOGGER.info("Detected that payment has already been made for the user on the month of the given payment date, rejecting payment request");
            return PaymentSecurityValidation.of("Payment already made and processed for payment date.");
        }

        return PaymentSecurityValidation.NONE;
    }


    DateTime getPaymentDateTimeAtStartOfMonth(DateTime paymentDate) {
        Assert.notNull(paymentDate, "paymentDate cannot be null");
        DateTime startOfMonthDateTime = paymentDate.toLocalDate().withDayOfMonth(1).toDateTimeAtStartOfDay();
        return startOfMonthDateTime;
    }

}
