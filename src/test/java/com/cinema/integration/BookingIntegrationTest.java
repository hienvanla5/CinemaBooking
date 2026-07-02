package com.cinema.integration;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.model.Theater;
import com.cinema.repository.*;
import com.cinema.service.BookingService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class BookingIntegrationTest {

    private BookingService bookingService;
    private BookingRepository bookingRepository;
    private MovieRepository movieRepository;
    private ShowtimeRepository showtimeRepository;
    private SeatRepository seatRepository;
    private TheaterRepository theaterRepository;

    @TempDir
    Path tempDir;
    private String movieFile;
    private String bookingFile;
    private String showtimeFile;
    private String seatFile;
    private String theaterFile;


    @BeforeEach
    void setUp() throws IOException {
        FileStorage fileStorage = new FileStorage();

        movieFile = tempDir.resolve("movies.csv").toString();
        bookingFile = tempDir.resolve("bookings.csv").toString();
        showtimeFile = tempDir.resolve("showtimes.csv").toString();
        seatFile = tempDir.resolve("seats.csv").toString();
        theaterFile = tempDir.resolve("theaters.csv").toString();

        List<String> movieLines = List.of(
                "1|Avengers|180",
                "2|Titanic|195",
                "3|Inception|148"
        );

        List<String> showtimeLines = Arrays.asList("1|2|3|2026-07-02 19:00:00", "2|2|3|2026-07-02 19:00:00", "3|2|3|2026-07-02 19:00:00");
        fileStorage.writeLines(showtimeFile, showtimeLines);
        theaterRepository = new TheaterRepository(theaterFile);
        theaterRepository.save(new Theater(3, "Hall C", 2, 5));


        fileStorage.writeLines(movieFile, movieLines);

        fileStorage.writeLines(bookingFile, List.of());

        movieRepository = new MovieRepository(movieFile);
        bookingRepository = new BookingRepository(bookingFile);
        showtimeRepository = new ShowtimeRepository(showtimeFile);
        seatRepository = new SeatRepository(seatFile);
        bookingService = new BookingService(bookingRepository, movieRepository, showtimeRepository, seatRepository);
    }

    @Test
    void testFullBookingFlow_Success() {
        Booking booking = bookingService.bookSeat(1, 5, "Alice");
        assertNotNull(booking);
        assertEquals(1, booking.getShowtimeId());
        assertEquals(5, booking.getSeatId());
        assertEquals("Alice", booking.getCustomerName());

        assertTrue(bookingRepository.isSeatBooked(1, 5));
        List<Integer> bookedSeats = bookingRepository.getBookedSeatsByShowtime(1);
        assertTrue(bookedSeats.contains(5));

        BookingRepository newRepo = new BookingRepository(bookingFile);
        assertTrue(newRepo.isSeatBooked(1, 5));
    }

    @Test
    void testFullBookingFlow_SeatUnavailable() {
        assertDoesNotThrow(() -> bookingService.bookSeat(1, 3, "Bob"));

        assertThrows(SeatUnavailableException.class, () -> {
            bookingService.bookSeat(1, 3, "Charlie");
        });
    }

    @Test
    void testFullBookingFlow_MovieNotFound() {
        assertThrows(InvalidInputException.class, () -> {
            bookingService.bookSeat(999, 1, "Diddy");
        });
    }

    @Test
    void testMultipleBookingsSequentially() {
        Booking b1 = bookingService.bookSeat(1, 7, "Eve");
        assertNotNull(b1);

        Booking b2 = bookingService.bookSeat(1, 8, "Frank");
        assertNotNull(b2);

        assertTrue(bookingRepository.isSeatBooked(1, 7));
        assertTrue(bookingRepository.isSeatBooked(1, 8));
        assertEquals(2, bookingRepository.getBookedSeatsByShowtime(1).size());
    }

    @Test
    void testCustomerNameWithSpaces() {
        Booking booking = bookingService.bookSeat(2, 1, "Nguyen Van A");
        assertNotNull(booking);
        assertEquals("Nguyen Van A", booking.getCustomerName());
        assertTrue(bookingRepository.isSeatBooked(2, 1));
    }

    @Test
    void testSeatIdExceedsMax_ThrowsException() {
        assertThrows(InvalidInputException.class, () -> {
            bookingService.bookSeat(1, 11, "Grace");
        });
    }

    @Test
    void testSeatIdNegative_ThrowsException() {
        assertThrows(InvalidInputException.class, () -> {
           bookingService.bookSeat(1, -1, "Henry");
        });
    }
}
