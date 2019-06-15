package com.imani.cash.domain.security.payment;

import com.imani.cash.domain.payment.EmbeddedPayment;
import com.imani.cash.domain.user.UserRecord;

/**
 * @author manyce400
 */
public interface IRentalPaymentHistorySecurityService {


    /**
     * Validates and enforces security concerns to make sure that rental payment does not breach any security concerns.
     *
     * @param userRecord
     * @param embeddedPayment
     * @return <code>true</code> IF this is a valid payment.
     */
    public boolean validatePayment(UserRecord userRecord, EmbeddedPayment embeddedPayment);

}
