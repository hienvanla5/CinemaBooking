package com.cinema.service;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.model.Movie;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.FileStorage;
import com.cinema.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    private BookingService bookingService;
    private BookingRepository bookingRepository;
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() throws IOException {
        String testMoviePath = "src/test/resources/data/movies.csv";
        String testBookingPath = "src/test/resources/data/bookings.csv";

        FileStorage fileStorage = new FileStorage();
        List<String> movieLines = Arrays.asList("1|Avengers|180", "2|Titanic|195");
        fileStorage.writeLines(testMoviePath, movieLines);

        List<String> bookingLines = Arrays.asList("1|3|Alice", "1|5|Bob");
        fileStorage.writeLines(testBookingPath, bookingLines);

        movieRepository = new MovieRepository(testMoviePath);
        bookingRepository = new BookingRepository(testBookingPath);
        bookingService = new BookingService(bookingRepository, movieRepository);
    }

    @Test
    void testBookSeat_Success() {
        assertTrue(bookingService.bookSeat(1, 7, "Charlie"));
        assertTrue(bookingRepository.isSeatBooked(1, 7));
    }

    @Test
    void testBookSeat_InvalidSeatId_ThrowsException() {
        assertThrows(InvalidInputException.class, () -> {
           bookingService.bookSeat(1, 99, "Hello");
        });
    }

    @Test
    void testBookSeat_EmptyCustomerName_ThrowsException() {
        assertThrows(InvalidInputException.class, () -> {
            bookingService.bookSeat(1, 2, "");
        });
    }

    @Test
    void testBookSeat_SeatAlreadyBooked_ThrowsUnavailableException() {
        assertThrows(SeatUnavailableException.class, () -> {
           bookingService.bookSeat(1, 3, "Charlie");
        });
    }

    @Test
    void testBookSeat_MovieNotFound_ThrowsException() {
        assertThrows(InvalidInputException.class, () -> {
           bookingService.bookSeat(3, 1, "Diddy");
        });
    }

}
