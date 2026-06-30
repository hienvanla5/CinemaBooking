package com.cinema.service;

import com.cinema.model.Booking;
import com.cinema.model.Movie;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    private BookingService bookingService;
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        String testMoviePath = "src/test/resources/data/movies.csv";
        String testBookingPath = "src/test/resources/data/bookings.csv";

        MovieRepository movieRepo = new MovieRepository(testMoviePath);
        bookingRepository = new BookingRepository(testBookingPath);

        movieRepo.clear();
        bookingRepository.clear();

        movieRepo.save(new Movie(1, "Test Movie", 120));
        bookingRepository.save(new Booking(1, 3, "Alice"));

        bookingService = new BookingService(bookingRepository, movieRepo);
    }

    @Test
    void testBookSeat_Success() {
        boolean result = bookingService.bookSeat(1, 7, "Charlie");
        assertTrue(result);
        assertTrue(bookingRepository.isSeatBooked(1, 7));
        assertEquals(2, bookingRepository.findAll().size());
    }

    @Test
    void testBookSeat_InvalidSeatId() {
        boolean result = bookingService.bookSeat(1, -1, "Eve");
        assertFalse(result);
        result = bookingService.bookSeat(1, 20, "Eve");
        assertFalse(result);
    }

    @Test
    void testBookSeat_EmptyCustomerName() {
        boolean result = bookingService.bookSeat(1, 2, "");
        assertFalse(result);
        result = bookingService.bookSeat(1, 2, null);
        assertFalse(result);
    }

    @Test
    void testBookSeat_MovieNotFound() {
        boolean result = bookingService.bookSeat(2, 1, "Frank");
        assertFalse(result);
    }
}
