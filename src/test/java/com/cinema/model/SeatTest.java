package com.cinema.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SeatTest {

    @Test
    void testCreateSeat() {
        Seat seat = new Seat(1, 2, 5, 3);
        assertEquals(1, seat.getId());
        assertEquals(2, seat.getTheaterId());
        assertEquals(5, seat.getRow());
        assertEquals(3, seat.getColumn());
    }
}
