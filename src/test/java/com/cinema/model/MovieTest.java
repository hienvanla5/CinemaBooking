package com.cinema.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTest {

    @Test
    void testCreateMovie() {
        Movie movie = new Movie(1, "Inception", 148);
        assertEquals(1, movie.getId());
        assertEquals("Inception", movie.getTitle());
        assertEquals(148, movie.getDuration());
    }

    @Test
    void testToString() {
        Movie movie = new Movie(2, "The Matrix", 136);
        String expected = "Movie{id=2, title='The Matrix', duration=136}";
        assertEquals(expected, movie.toString());
    }
}
