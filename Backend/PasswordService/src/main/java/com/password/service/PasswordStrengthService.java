package com.password.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordStrengthService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIALS = "!@#$%^&*()_+";

    public String evaluatePasswordStrength(String password) {
        if (password.length() < 8) {
            return "Weak";
        }

        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> SPECIALS.contains(Character.toString(ch)));

        if (hasUpper && hasLower && hasDigit && hasSpecial) {
            return "Strong";
        } else if ((hasUpper && hasLower && hasDigit) || (hasUpper && hasLower && hasSpecial) || (hasUpper && hasDigit && hasSpecial) || (hasLower && hasDigit && hasSpecial)) {
            return "Medium";
        } else {
            return "Weak";
        }
    }
}