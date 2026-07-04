package com.cinema.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExceptionTest {

    @Test
    void testBookingAddException() {
        BookingAppException ex = new BookingAppException("Test message");
        assertEquals("Test message", ex.getMessage());
    }

    @Test
    void testInvalidInputException() {
        InvalidInputException ex = new InvalidInputException("Invalid input");
        assertEquals("Invalid input", ex.getMessage());

        assertTrue(ex instanceof BookingAppException);
    }

    @Test
    void testSeatUnavailableException() {
        SeatUnavailableException ex = new SeatUnavailableException("Seat taken");
        assertEquals("Seat taken", ex.getMessage());

        assertTrue(ex instanceof BookingAppException);
    }

    @Test
    void testThrowAndCatch() {
        try {
            throw new InvalidInputException("Custom error");
        } catch (InvalidInputException e) {
            assertEquals("Custom error", e.getMessage());
        }
    }
}
