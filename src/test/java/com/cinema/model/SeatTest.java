package com.cinema.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SeatTest {

    @Test
    void testCreateSeat() {
        Seat seat = new Seat(10);
        assertEquals(10, seat.getId());
        assertFalse(seat.isBooked());
    }

    @Test
    void testSetBooked() {
        Seat seat = new Seat(5);
        assertFalse(seat.isBooked());
        seat.setBooked(true);
        assertTrue(seat.isBooked());
    }
}
