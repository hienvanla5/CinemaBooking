package com.cinema.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BookingTest {

    @Test
    void testCreateBooking() {
        Booking booking = new Booking(1, 1, "Alice");
        assertEquals(1, booking.getShowtimeId());
        assertEquals(1, booking.getSeatId());
        assertEquals("Alice", booking.getCustomerName());
    }
}
