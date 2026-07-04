package com.cinema.factory;

import com.cinema.model.Booking;

/**
 * Factory implementation for creating bookings for VIP customers.
 * <p>
 * A VIP booking includes a membership level and a discount rate,
 * which are stored in the created {@link Booking} object.
 */
public class VIPBookingFactory implements BookingFactory {

    private final int vipLevel;
    private final double discount;

    /**
     * Creates a VIP booking factory with the specified membership level
     * and discount percentage.
     *
     * @param vipLevel the VIP membership level
     * @param discount the discount percentage applied to the booking
     */
    public VIPBookingFactory(int vipLevel, double discount) {
        this.vipLevel = vipLevel;
        this.discount = discount;
    }

    /**
     * Creates a booking for a VIP customer.
     *
     * @param showtimeId   the ID of the showtime
     * @param seatId       the ID of the selected seat
     * @param customerName the customer's name
     * @param price        the final ticket price after pricing calculation
     * @return a new {@link Booking} instance containing the specified
     *         VIP level and discount information
     */
    @Override
    public Booking createBooking(int showtimeId, int seatId, String customerName, double price) {
        return new Booking(showtimeId, seatId, customerName, vipLevel, discount, price);
    }
}