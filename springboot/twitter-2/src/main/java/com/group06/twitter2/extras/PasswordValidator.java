package com.group06.twitter2.extras;

import java.util.*;

public class PasswordValidator {
    private static final int MIN_LENGTH = 8;

    public static boolean validatePassword(String password) {
        if (password == null || password.length() < MIN_LENGTH) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;
        char[] characters = password.toCharArray();

        for (int i = 0; i < characters.length; i++) {
            char c = characters[i];
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (isSpecialCharacter(c)) {
                hasSpecialCharacter = true;
            }
        }
        return hasUppercase && hasLowercase && hasDigit && hasSpecialCharacter;
    }

    private static boolean isSpecialCharacter(char c) {
        Set<Character> specialCharacters = Set.of('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '{', '}', '[', ']', ':', ';', '<', '>', ',', '.', '?', '/', '\\', '|', '~', '`');
        return specialCharacters.contains(c);
    }
}
