package com.imani.cash.domain.service.security;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Service(AESEncryptionService.SPRING_BEAN)
public class AESEncryptionService implements IAESEncryptionService {



    private IvParameterSpec ivParameterSpec;

    private SecretKeySpec secretKeySpec;

    private Cipher cipher;

    private static final String UNIQUE_ALGORITHM_IDENTIFIER = "-AES";

    private static final String PUBLIC_KEY = "ssdkF$HUy2A#D%kd";

    private static final String PRIVATE_KEY = "weJiSEvR5yAC5ftB";


    public static final String SPRING_BEAN = "com.imani.cash.domain.service.security.AESEncryptionService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AESEncryptionService.class);

    public AESEncryptionService() {
        try {
            LOGGER.info("Attempting to initialize AES Security encryption services...");
            ivParameterSpec = new IvParameterSpec(PUBLIC_KEY.getBytes("UTF-8"));
            secretKeySpec = new SecretKeySpec(PRIVATE_KEY.getBytes("UTF-8"), "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize AES Security settings", e);
        }
    }

    @Override
    public String encrypt(String stringToEncrypt) throws EncryptionException {
        Assert.notNull(stringToEncrypt, "stringToEncrypt cannot be null");
        Assert.isTrue(!isEncryptedString(stringToEncrypt), "String to encrypt is already encrypted");

        try {
            LOGGER.debug("Encryption has been invoked on String");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(stringToEncrypt.getBytes());
            String algorithmEncodedString = Base64.encodeBase64String(encrypted) + UNIQUE_ALGORITHM_IDENTIFIER;
            return algorithmEncodedString;
        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }

    @Override
    public String decrypt(String stringToDecrypt) throws EncryptionException {
        Assert.notNull(stringToDecrypt, "stringToDecrypt cannot be null");
        Assert.isTrue(isEncryptedString(stringToDecrypt), "String to decrypt has to be encrypted first");

        // First strip out the algorithm portion of the string
        int algorithmIndex = stringToDecrypt.indexOf("-");
        String newStringToDecrypt = stringToDecrypt.substring(0, algorithmIndex);

        try {
            LOGGER.debug("Decryption has been invoked on String");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(newStringToDecrypt));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }


    @Override
    public boolean isEncryptedString(String stringToTest) {
        Assert.notNull(stringToTest, "stringToTest cannot be null");
        return stringToTest.contains(UNIQUE_ALGORITHM_IDENTIFIER);
    }
}
