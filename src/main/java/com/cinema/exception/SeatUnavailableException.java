package com.cinema.exception;

/**
 * Exception thrown when a seat cannot be booked because it is
 * already reserved or otherwise unavailable.
 * <p>
 * This exception is typically raised during the booking process
 * when multiple users attempt to reserve the same seat or when
 * the selected seat has already been booked.
 */
public class SeatUnavailableException extends BookingAppException {

    /**
     * Constructs a new seat unavailable exception with the specified detail message.
     *
     * @param message the detail message describing why the seat is unavailable
     */
    public SeatUnavailableException(String message) {
        super(message);
    }
}