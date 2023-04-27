package com.kdew.dewlms.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {

    //비교
    public static boolean equals(String plaintext, String hashed) {

        if (plaintext == null || plaintext.length() < 1) {
            return false;
        }

        if (hashed == null || hashed.length() < 1) {
            return false;
        }
        return BCrypt.checkpw(plaintext, hashed);
    }

    // 해시 값 주기
    public static String encPassword(String plaintext) {
        if (plaintext == null || plaintext.length() < 1) {
            return "";
        }
        return BCrypt.hashpw(plaintext,BCrypt.gensalt());
    }
}
