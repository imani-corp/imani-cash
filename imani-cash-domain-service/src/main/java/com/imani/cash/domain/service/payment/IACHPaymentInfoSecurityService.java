package com.imani.cash.domain.service.payment;

import com.imani.cash.domain.payment.ACHPaymentInfo;

/**
 * @author manyce400
 */
public interface IACHPaymentInfoSecurityService {


    public void execACHPaymentInfoEncryption(ACHPaymentInfo achPaymentInfo);


    public void execACHPaymentInfoDecryption(ACHPaymentInfo achPaymentInfo);


}
