package com.zachvoxwattz.utils;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Class responsible for generating WebSocket keys
 * for clients to communicate with the server.
 */
public class AuthTokenGenerator {
    /**
     * Used for aiding in generating random keys. 
     */
    private static SecureRandom secRand = new SecureRandom();

    /**
     * Generates a randomized key.
     * @return Key string.
     */
    public static String generateToken() {
        KeyGenerator tokenGen = null;

        try {
            tokenGen = KeyGenerator.getInstance("HmacSHA256");
            tokenGen.init(secRand);
        }
        catch (Exception e) { e.printStackTrace(); }
        
        /*
            What is more secure than a securely randomly generated key?
            TWO of them!
         */
        // SecretKey generatedKey1 = keyGen.generateKey();
        // SecretKey generatedKey2 = keyGen.generateKey();
        // String generatedKeyString1 = Base64.getEncoder().encodeToString(generatedKey1.getEncoded());
        // String generatedKeyString2 = Base64.getEncoder().encodeToString(generatedKey2.getEncoded());
        
        // return String.format("%s$%s", generatedKeyString1, generatedKeyString2);
        
        // But it would be... redundant for this app. Therefore, only 1 key is used.
        SecretKey generatedToken = tokenGen.generateKey();
        String generatedTokenString = Base64.getEncoder().encodeToString(generatedToken.getEncoded());
        
        return generatedTokenString;
    }
}
