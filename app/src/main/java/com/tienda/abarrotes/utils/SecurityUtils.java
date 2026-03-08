package com.tienda.abarrotes.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SecurityUtils {

    private SecurityUtils() {
        // Evita instanciación
    }

    public static String hashPassword(String password) {
        if (password == null) {
            return "";
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }

        String plainHash = hashPassword(plainPassword);
        return plainHash.equals(hashedPassword);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b));
        }

        return stringBuilder.toString();
    }
}