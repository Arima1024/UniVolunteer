package com.univolunteer.user.utils;

import java.util.regex.Pattern;

public class PasswordUtils {

    private static final Pattern STRONG_PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$"
    );

    public static boolean isStrongPassword(String password) {
        return STRONG_PASSWORD_PATTERN.matcher(password).matches();
    }
}
