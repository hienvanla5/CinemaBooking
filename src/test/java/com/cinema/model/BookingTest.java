package com.cinema.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BookingTest {

    @Test
    void testCreateBooking() {
        Booking booking = new Booking(5, 2, "Alice");
        assertEquals(5, booking.getMovieId());
        assertEquals(2, booking.getSeatId());
        assertEquals("Alice", booking.getCustomerName());
    }
}
