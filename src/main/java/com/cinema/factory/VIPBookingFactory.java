package com.cinema.factory;

import com.cinema.model.Booking;

public class VIPBookingFactory implements BookingFactory {

    private final int vipLevel;
    private final double discount;

    public VIPBookingFactory(int vipLevel, double discount) {
        this.vipLevel = vipLevel;
        this.discount = discount;
    }

    @Override
    public Booking createBooking(int showtimeId, int seatId, String customerName) {
        return new Booking(showtimeId, seatId, customerName, vipLevel, discount);
    }
}
