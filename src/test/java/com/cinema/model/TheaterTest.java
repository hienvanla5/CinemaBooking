package com.cinema.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TheaterTest {

    @Test
    void testCreateTheater() {
        Theater theater = new Theater(1, "Hall A", 10, 12);
        assertEquals(1, theater.getId());
        assertEquals("Hall A", theater.getName());
        assertEquals(10, theater.getTotalRows());
        assertEquals(12, theater.getTotalColumns());
    }
}
