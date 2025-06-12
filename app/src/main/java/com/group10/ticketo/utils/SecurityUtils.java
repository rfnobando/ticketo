package com.group10.ticketo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class SecurityUtils {
    public final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(7);

    public static String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
