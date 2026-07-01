package com.cinema.service;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.FileStorage;
import com.cinema.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    private BookingService bookingService;
    private BookingRepository bookingRepository;
    private MovieRepository movieRepository;

    @TempDir
    Path tempDir;
    private String movieFile;
    private String bookingFile;

    @BeforeEach
    void setUp() throws IOException {
        movieFile = tempDir.resolve("movies.csv").toString();
        bookingFile = tempDir.resolve("bookings.csv").toString();

        FileStorage fileStorage = new FileStorage();
        List<String> movieLines = Arrays.asList("1|Avengers|180", "2|Titanic|195");
        fileStorage.writeLines(movieFile, movieLines);

        List<String> bookingLines = Arrays.asList("1|3|Alice", "1|5|Bob");
        fileStorage.writeLines(bookingFile, bookingLines);

        movieRepository = new MovieRepository(movieFile);
        bookingRepository = new BookingRepository(bookingFile);
        bookingService = new BookingService(bookingRepository, movieRepository);
    }

    @Test
    void testBookSeat_Success_ReturnsBooking() {
        Booking booking = bookingService.bookSeat(1, 7, "Charlie");
        assertNotNull(booking);
        assertEquals(1, booking.getMovieId());
        assertEquals(7, booking.getSeatId());
        assertEquals("Charlie", booking.getCustomerName());
    }

    @Test
    void testBookSeat_InvalidSeatId_ThrowsException() {
        assertThrows(InvalidInputException.class, () -> {
           bookingService.bookSeat(1, 11, "Hello");
        });
        assertThrows(InvalidInputException.class, () -> {
            bookingService.bookSeat(1, 0, "Frank");
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
