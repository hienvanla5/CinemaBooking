package com.cinema.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShowtimeTest {

    @Test
    void testCreateShowtime() {
        Showtime showtime = new Showtime(1, 2, 3, "2026-06-07 14:30");
        assertEquals(1, showtime.getId());
        assertEquals(2, showtime.getMovieId());
        assertEquals(3, showtime.getTheaterId());
        assertEquals("2026-06-07 14:30", showtime.getStartTime());
    }
}
