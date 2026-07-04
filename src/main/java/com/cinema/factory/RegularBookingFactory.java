package com.cinema.factory;

import com.cinema.model.Booking;

/**
 * Factory implementation for creating bookings for regular customers.
 * <p>
 * Regular bookings do not include any VIP benefits. The created booking
 * has:
 * <ul>
 *     <li>VIP level = 0</li>
 *     <li>Discount = 0%</li>
 * </ul>
 */
public class RegularBookingFactory implements BookingFactory {

    /**
     * Creates a booking for a regular customer.
     *
     * @param showtimeId   the ID of the showtime
     * @param seatId       the ID of the selected seat
     * @param customerName the customer's name
     * @param price        the final ticket price
     * @return a new {@link Booking} instance for a regular customer
     */
    @Override
    public Booking createBooking(int showtimeId, int seatId, String customerName, double price) {
        return new Booking(showtimeId, seatId, customerName, 0, 0, price);
    }
}