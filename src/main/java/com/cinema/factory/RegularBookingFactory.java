package com.cinema.factory;

import com.cinema.model.Booking;

public class RegularBookingFactory implements BookingFactory {

    @Override
    public Booking createBooking(int showtimeId, int seatId, String customerName) {
        return new Booking(showtimeId, seatId, customerName);
    }
}
