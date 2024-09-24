package com.egg.news.utils;

import com.egg.news.exceptions.UserInputException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Utility class for input validation.
 */
public class ValidationUtils {

    private ValidationUtils() {
        // Private constructor to prevent instantiation, as this is a utility class.
    }

    /**
     * Checks if the image is null or empty.
     *
     * @param image The image MultipartFile to be checked.
     * @throws UserInputException If the image MultipartFile is null or empty.
     */
    public static void validateImage(MultipartFile image) {
        if (image == null) {
            throw new UserInputException("The input cannot be empty");
        }
    }

    /**
     * Checks if the input string is null or empty.
     *
     * @param input The input string to be checked.
     * @return true if the input is null or empty, false otherwise.
     */
    public static boolean isInvalidInput(String input) {
        return input == null || input.trim().isEmpty();
    }

    /**
     * Validates multiple input strings to ensure they are not null or empty.
     *
     * @param inputs The input strings to be validated.
     * @throws UserInputException If any of the input strings is null or empty.
     */
    public static void validateInput(String... inputs) throws UserInputException {
        for (String input : inputs) {
            if (isInvalidInput(input)) {
                throw new UserInputException("The input cannot be empty");
            }
        }
    }
}
