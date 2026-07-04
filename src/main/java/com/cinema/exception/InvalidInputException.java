package com.cinema.exception;

/**
 * Exception thrown when user input is invalid or does not satisfy
 * the application's validation rules.
 * <p>
 * Examples include invalid IDs, empty customer names, or incorrectly
 * formatted input values.
 */
public class InvalidInputException extends BookingAppException {

    /**
     * Constructs a new invalid input exception with the specified detail message.
     *
     * @param message the detail message describing the validation error
     */
    public InvalidInputException(String message) {
        super(message);
    }
}