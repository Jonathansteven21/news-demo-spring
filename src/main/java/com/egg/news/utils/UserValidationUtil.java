package com.egg.news.utils;


import com.egg.news.exceptions.UserInputException;

import java.util.Objects;

public class UserValidationUtil {
    private UserValidationUtil() {
        // Private constructor to prevent instantiation
    }


    public static void validateUser(String name, String email, String password, String password2) {
        validateName(name);
        validatePassword(password,password2);
        validateEmail(email);
    }

    private static void validateEmail(String email) {
        if (email == null) {
            throw new UserInputException("Email cannot be null");
        }

        String[] parts = email.split("@");

        if (parts.length != 2) {
            throw new UserInputException("Email must contain one '@' character");
        }

        String localPart = parts[0];
        String domainPart = parts[1];

        if (localPart.isEmpty() || localPart.length() > 64) {
            throw new UserInputException("Local part of email must be between 1 and 64 characters");
        }

        if (domainPart.isEmpty() || domainPart.length() > 255) {
            throw new UserInputException("Domain part of email must be between 1 and 255 characters");
        }
    }


    private static void validatePassword(String password, String password2) {
        if (!Objects.equals(password, password2)) {
            throw new UserInputException("Passwords should match");
        }

        if (password == null) {
            throw new UserInputException("Passwords cannot be null");
        }

        if (password.length() < 8) {
            throw new UserInputException("Password must be at least 8 characters long");
        }

        if (!password.matches(".*\\d.*")) {
            throw new UserInputException("Password must contain at least one digit");
        }

        if (!password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")) {
            throw new UserInputException("Password must contain at least one uppercase and one lowercase letter");
        }

        if (!password.matches(".*[!@#$%^&*()].*")) {
            throw new UserInputException("Password must contain at least one special character (!@#$%^&*())");
        }
    }


    private static void validateName(String name) {
        if (name == null) {
            throw new UserInputException("Name cannot be null");
        }

        if (name.length() < 2) {
            throw new UserInputException("Name must be at least 2 characters long");
        }

        if (!name.matches("[a-zA-Z ]+")) {
            throw new UserInputException("Name can only contain letters and spaces");
        }
    }

}
