package com.imani.cash.domain.service.payment;

import com.imani.cash.domain.payment.ACHPaymentInfo;
import com.imani.cash.domain.service.security.AESEncryptionService;
import com.imani.cash.domain.service.security.EncryptionException;
import com.imani.cash.domain.service.security.IAESEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author manyce400
 */
@Service(ACHPaymentInfoSecurityService.SPRING_BEAN)
public class ACHPaymentInfoSecurityService implements IACHPaymentInfoSecurityService {



    @Autowired
    @Qualifier(AESEncryptionService.SPRING_BEAN)
    private IAESEncryptionService iaesEncryptionService;

    public static final String SPRING_BEAN = "com.imani.cash.domain.service.payment.ACHPaymentInfoSecurityService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ACHPaymentInfoSecurityService.class);


    @Override
    public void execACHPaymentInfoEncryption(ACHPaymentInfo achPaymentInfo) {
        // Heads up:  Calling any of the encrypt/decrypt methods when String is already decrypted or encrypted will cause an exception to be thrown.

        try {
            String encryptedBankAcctNumber = iaesEncryptionService.encrypt(achPaymentInfo.getBankAcctNumber());
            achPaymentInfo.setBankAcctNumber(encryptedBankAcctNumber);

            String encryptedRoutingNumber = iaesEncryptionService.encrypt(achPaymentInfo.getRoutingNumber());
            achPaymentInfo.setRoutingNumber(encryptedRoutingNumber);
        } catch (EncryptionException e) {
            throw new RuntimeException("Failed to encrypt ACHPaymentInfo", e);
        }

    }

    @Override
    public void execACHPaymentInfoDecryption(ACHPaymentInfo achPaymentInfo) {
        try {
            String decryptedBankAcctNumber = iaesEncryptionService.decrypt(achPaymentInfo.getBankAcctNumber());
            achPaymentInfo.setBankAcctNumber(decryptedBankAcctNumber);

            String decryptedRoutingNumber = iaesEncryptionService.decrypt(achPaymentInfo.getRoutingNumber());
            achPaymentInfo.setRoutingNumber(decryptedRoutingNumber);
        } catch (EncryptionException e) {
            throw new RuntimeException("Failed to encrypt ACHPaymentInfo", e);
        }
    }


}
