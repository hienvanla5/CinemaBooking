package com.cinema.integration;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.factory.RegularBookingFactory;
import com.cinema.model.Booking;
import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.repository.*;
import com.cinema.service.BookingService;

import static org.junit.jupiter.api.Assertions.*;

import com.cinema.strategy.NormalPricingStrategy;
import com.cinema.strategy.PriceCalculator;
import com.cinema.util.FileStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
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
        movieFile = tempDir.resolve("movies.csv").toString();
        bookingFile = tempDir.resolve("bookings.csv").toString();
        showtimeFile = tempDir.resolve("showtimes.csv").toString();
        seatFile = tempDir.resolve("seats.csv").toString();
        theaterFile = tempDir.resolve("theaters.csv").toString();

        FileStorage.getInstance().writeLines(movieFile, List.of("1|Avengers|180", "2|Titanic|195"));

        FileStorage.getInstance().writeLines(theaterFile, List.of("1|Hall A|5|5"));

        seatRepository = new SeatRepository(seatFile);
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                int id = row * 5 + col + 1;
                seatRepository.save(new Seat(id, 1, row, col));
            }
        }

        FileStorage.getInstance().writeLines(showtimeFile, List.of("1|1|1|2026-07-11 14:00"));

        FileStorage.getInstance().writeLines(bookingFile, List.of());

        movieRepository = new MovieRepository(movieFile);
        theaterRepository = new TheaterRepository(theaterFile);
        bookingRepository = new BookingRepository(bookingFile);
        showtimeRepository = new ShowtimeRepository(showtimeFile);


        bookingService = new BookingService(bookingRepository, movieRepository, showtimeRepository, seatRepository, new RegularBookingFactory(), new PriceCalculator(new NormalPricingStrategy()));
    }

    @Test
    void testFullBookingFlow_Success() {
        Booking booking = bookingService.bookSeat(1, 5, "Alice");
        bookingService.flushBookings();
        assertNotNull(booking);
        assertEquals(1, booking.getShowtimeId());
        assertEquals(5, booking.getSeatId());
        assertEquals("Alice", booking.getCustomerName());

        assertTrue(bookingRepository.isSeatBooked(1, 5));

        List<Integer> booked = bookingRepository.getBookedSeatsByShowtime(1);
        assertTrue(booked.contains(5));
        assertEquals(1, booked.size());

        assertThrows(SeatUnavailableException.class, () -> bookingService.bookSeat(1, 5, "Bob"));
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
        bookingService.flushBookings();
        assertNotNull(b1);

        Booking b2 = bookingService.bookSeat(1, 8, "Frank");
        bookingService.flushBookings();
        assertNotNull(b2);

        assertTrue(bookingRepository.isSeatBooked(1, 7));
        assertTrue(bookingRepository.isSeatBooked(1, 8));
        assertEquals(2, bookingRepository.getBookedSeatsByShowtime(1).size());
    }

    @Test
    void testCustomerNameWithSpaces() {
        Booking booking = bookingService.bookSeat(1, 3, "Nguyen Van A");
        bookingService.flushBookings();
        assertNotNull(booking);
        assertEquals("Nguyen Van A", booking.getCustomerName());
        assertTrue(bookingRepository.isSeatBooked(1, 3));
    }

    @Test
    void testSeatIdExceedsMax_ThrowsException() {
        assertThrows(InvalidInputException.class, () -> {
            bookingService.bookSeat(1, 26, "Grace");
        });
    }

    @Test
    void testSeatIdNegative_ThrowsException() {
        assertThrows(InvalidInputException.class, () -> {
           bookingService.bookSeat(1, -1, "Henry");
        });
    }

    @Test
    void testDeleteTheaterWithShowtime_ThrowsException() {
        theaterRepository.delete(1);

    }

    @Test
    void testAddShowtimeWithInvalidMovie_ThrowsException() {
        Showtime invalid = new Showtime(2, 999, 1, "2026-07-11 15:00");
//        assertThrows(InvalidInputException.class, () -> {
//
//        });
    }

    @Test
    void testBookSeatWithInvalidSeat_ThrowsException() {
        assertThrows(InvalidInputException.class, () -> {
            bookingService.bookSeat(1, 999, "Eve");
        });
    }

    @Test
    void testBookSeatWithSeatOutOfRange_ThrowsException() {

    }

    @Test
    void testBookSeat_SeatNotInTheater_ThrowsException() {

    }

    @Test
    void testSeatRepository_FindByTheaterId() {
        List<Seat> seats = seatRepository.findByTheaterId(1);
        assertEquals(25, seats.size());
        seats = seatRepository.findByTheaterId(2);
        assertTrue(seats.isEmpty());
    }

    @Test
    void testShowtimeRepository_FindByMovieId() {
        List<Showtime> showtimes = showtimeRepository.findByMovieId(1);
        assertEquals(1, showtimes.size());
        showtimes = showtimeRepository.findByMovieId(2);
        assertTrue(showtimes.isEmpty());
    }

    @Test
    void testGetAvailableSeats() {
        bookingService.bookSeat(1, 5, "Alice");
        bookingService.flushBookings();
        List<Seat> available = bookingService.getAvailableSeats(1);
        assertEquals(24, available.size());
        assertFalse(available.stream().anyMatch(s -> s.getId() == 5));
    }
}
