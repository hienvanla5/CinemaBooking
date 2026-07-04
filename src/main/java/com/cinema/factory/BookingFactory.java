package com.cinema.factory;

import com.cinema.model.Booking;

public interface BookingFactory {

    Booking createBooking(int showtimeId, int seatId, String customerName, double price);
}
