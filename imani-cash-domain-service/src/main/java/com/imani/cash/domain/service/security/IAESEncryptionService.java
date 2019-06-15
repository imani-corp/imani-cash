package com.imani.cash.domain.service.security;

/**
 * @author manyce400
 */
public interface IAESEncryptionService {


    public String encrypt(String stringToEncrypt) throws EncryptionException;


    public String decrypt(String stringToDecrypt) throws EncryptionException;


    public boolean isEncryptedString(String stringToTest);

}
