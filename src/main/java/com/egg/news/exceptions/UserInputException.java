package com.egg.news.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

/**
 * Custom exception class for handling user input-related errors.
 * Extends RuntimeException for unchecked exception behavior.
 */
public class UserInputException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    // Logger for logging error messages
    private static final Logger logger = LoggerFactory.getLogger(UserInputException.class);

    /**
     * Constructor to create a UserInputException with a specified error message.
     *
     * @param message The error message describing the user input issue.
     */
    public UserInputException(String message) {
        super(message);
    }

    /**
     * Method to handle the exception by logging error messages.
     * This method is called when an instance of this exception is caught.
     */
    public void handle() {
        logger.error("Error processing user input");
        logger.error("Error message: {}", getMessage());
    }
}
