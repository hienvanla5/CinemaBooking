package com.cinema.exception;

/**
 * Base runtime exception for the cinema booking application.
 * <p>
 * All custom unchecked exceptions in the application should extend
 * this class to provide a common exception hierarchy.
 */
public class BookingAppException extends RuntimeException {

    /**
     * Constructs a new booking application exception with the specified detail message.
     *
     * @param message the detail message describing the cause of the exception
     */
    public BookingAppException(String message) {
        super(message);
    }
}