package com.gov.iti.business.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Hasher {
    private static volatile Hasher instance = null;

    private Hasher() {}

    public static Hasher getInstance() {
        if (instance == null) {
            synchronized (Hasher.class) {
                if (instance == null) {
                    instance = new Hasher();
                }
            }
        }
        return instance;
    }

    public String hash(String originalString) {
        if (originalString.length() == 64)
            return originalString;

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        byte[] hash = digest.digest(
                originalString.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
