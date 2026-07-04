package com.cinema.factory;

import com.cinema.model.Booking;

/**
 * Factory interface for creating {@link Booking} objects.
 * <p>
 * Different implementations can create different booking types,
 * such as regular or VIP bookings.
 */
public interface BookingFactory {

    /**
     * Creates a booking.
     *
     * @param showtimeId the showtime ID
     * @param seatId the seat ID
     * @param customerName the customer name
     * @param price the ticket price
     * @return the created booking
     */
    Booking createBooking(int showtimeId, int seatId, String customerName, double price);
}