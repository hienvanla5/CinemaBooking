package com.cinema.service;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.model.Seat;
import com.cinema.model.Theater;
import com.cinema.repository.*;
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
        movieFile = tempDir.resolve("movies.csv").toString();
        bookingFile = tempDir.resolve("bookings.csv").toString();
        showtimeFile = tempDir.resolve("showtimes.csv").toString();
        seatFile = tempDir.resolve("seats.csv").toString();
        theaterFile = tempDir.resolve("theaters.csv").toString();

        FileStorage fileStorage = new FileStorage();
        List<String> showtimeLines = Arrays.asList("1|2|3|2026-07-02 09:00:00", "2|2|3|2026-07-02 19:00:00");
        fileStorage.writeLines(showtimeFile, showtimeLines);

        List<String> bookingLines = Arrays.asList("1|1|LaVanHien|2026-07-02 10:00:00", "1|2|HienVanLa|2026-07-03 11:00:00");
        fileStorage.writeLines(bookingFile, bookingLines);

        theaterRepository = new TheaterRepository(theaterFile);
        theaterRepository.save(new Theater(3, "Hall C", 4, 4));

        movieRepository = new MovieRepository(movieFile);
        bookingRepository = new BookingRepository(bookingFile);
        showtimeRepository = new ShowtimeRepository(showtimeFile);
        seatRepository = new SeatRepository(seatFile);
        bookingService = new BookingService(bookingRepository, movieRepository, showtimeRepository, seatRepository);
    }

    @Test
    void testBookSeat_Success_ReturnsBooking() {
        Booking booking = bookingService.bookSeat(1, 7, "Charlie");
        assertNotNull(booking);
        assertEquals(1, booking.getShowtimeId());
        assertEquals(7, booking.getSeatId());
        assertEquals("Charlie", booking.getCustomerName());

    }

    @Test
    void testBookSeat_InvalidSeatId_ThrowsException() {
        assertThrows(InvalidInputException.class, () -> {
           bookingService.bookSeat(1, -1, "Hello");
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
           bookingService.bookSeat(1, 2, "Charlie");
        });
    }

    @Test
    void testBookSeat_MovieNotFound_ThrowsException() {
        assertThrows(InvalidInputException.class, () -> {
           bookingService.bookSeat(3, 1, "Diddy");
        });
    }

    @Test
    void testBookSeat_WrongTheater() {
        assertThrows(InvalidInputException.class, () -> {
            bookingService.bookSeat(2, 17, "Charlie");
        });
    }

    @Test
    void testBookSeat_SeatUnavailable() {
        bookingService.bookSeat(1, 4, "Charlie");
        assertThrows(SeatUnavailableException.class, () -> {
            bookingService.bookSeat(1, 4, "Diddy");
        });
    }

    @Test
    void testGetAvailableSeats() {
        List<Seat> available = bookingService.getAvailableSeats(1);
        assertEquals(14, available.size());
        assertFalse(available.stream().anyMatch(s -> s.getId() == 1));
        assertFalse(available.stream().anyMatch(s -> s.getId() == 2));
        assertTrue(available.stream().anyMatch(s -> s.getId() == 3));
        assertTrue(available.stream().anyMatch(s -> s.getId() == 4));
    }
}
