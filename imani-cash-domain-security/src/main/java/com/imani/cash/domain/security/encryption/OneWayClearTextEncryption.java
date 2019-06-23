package com.imani.cash.domain.security.encryption;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author manyce400
 */
@Service(OneWayClearTextEncryption.SPRING_BEAN)
public class OneWayClearTextEncryption implements IOneWayClearTextEncryption {


    public static final String SPRING_BEAN = "com.imani.cash.domain.security.encryption.OneWayClearTextEncryption";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(OneWayClearTextEncryption.class);

    @Override
    public String encryptClearText(String clearText) {
        LOGGER.debug("Attempting to encrypt clearText.....");
        String encoded = BCrypt.hashpw(clearText.toString(), BCrypt.gensalt(4));
        return encoded;
    }

    @Override
    public boolean matchesEncryptedValue(String clearText, String encryptedText) {
        LOGGER.debug("Checking to see if clear text matches encrpted value....");
        return BCrypt.checkpw(clearText, encryptedText);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("boss");
        System.out.println("result = " + result);
    }

}
