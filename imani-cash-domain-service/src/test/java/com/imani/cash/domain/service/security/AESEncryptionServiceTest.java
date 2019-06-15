package com.imani.cash.domain.service.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author manyce400 
 */
@RunWith(MockitoJUnitRunner.class)
public class AESEncryptionServiceTest {
    
    
    private IAESEncryptionService iaesEncryptionService;
    
    @Before
    public void before() {
        iaesEncryptionService = new AESEncryptionService();
    }
    
    @Test
    public void testEncrypt() throws EncryptionException {
        String encrypted = iaesEncryptionService.encrypt("Hello World");
        Assert.assertFalse(encrypted.equals("Hello World"));
    }

    @Test
    public void testDecrypt() throws EncryptionException {
        String encrypted = iaesEncryptionService.encrypt("Hello World");
        String decrypted = iaesEncryptionService.decrypt(encrypted);
        Assert.assertEquals("Hello World", decrypted);
    }

    @Test
    public void testIsEncryptedTrue() throws EncryptionException {
        String encrypted = iaesEncryptionService.encrypt("Hello World");
        boolean isEncrypted = iaesEncryptionService.isEncryptedString(encrypted);
        Assert.assertTrue(isEncrypted);

        encrypted = iaesEncryptionService.encrypt("hollls0eidiiaisidi");
        isEncrypted = iaesEncryptionService.isEncryptedString(encrypted);
        Assert.assertTrue(isEncrypted);
    }

    @Test
    public void testIsEncryptedFalse() throws EncryptionException {
        boolean isEncrypted = iaesEncryptionService.isEncryptedString("Hello World");
        Assert.assertFalse(isEncrypted);

        isEncrypted = iaesEncryptionService.isEncryptedString("Hello World==-");
        Assert.assertFalse(isEncrypted);
    }
}
