package com.cinema.service;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.factory.RegularBookingFactory;
import com.cinema.factory.VIPBookingFactory;
import com.cinema.model.Booking;
import com.cinema.model.Seat;
import com.cinema.repository.*;
import com.cinema.util.FileStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    @TempDir
    Path tempDir;

    private BookingService bookingService;

    private BookingRepository bookingRepository;
    private ShowtimeRepository showtimeRepository;
    private SeatRepository seatRepository;
    private BookingValidator validator;
    private BookingPriceService priceService;
    private BookingManager manager;

    @BeforeEach
    void setUp() throws IOException {

        String movieFile = tempDir.resolve("movies.csv").toString();
        String theaterFile = tempDir.resolve("theaters.csv").toString();
        String seatFile = tempDir.resolve("seats.csv").toString();
        String showtimeFile = tempDir.resolve("showtimes.csv").toString();
        String bookingFile = tempDir.resolve("bookings.csv").toString();

        FileStorage.getInstance().writeLines(movieFile, List.of("2|Avengers|180"));

        FileStorage.getInstance().writeLines(theaterFile, List.of("3|Hall C|4|4"));

        seatRepository = new SeatRepository(seatFile);

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                int id = r * 4 + c + 1;
                seatRepository.save(new Seat(id, 3, r, c));
            }
        }

        FileStorage.getInstance().writeLines(showtimeFile, Arrays.asList("1|2|3|2026-07-07 09:00", "2|2|3|2026-07-08 09:00"));

        FileStorage.getInstance().writeLines(bookingFile, Arrays.asList("1|1|LaVanHien|50000", "1|2|hienvanla|5000"));

        new MovieRepository(movieFile);
        new TheaterRepository(theaterFile);

        bookingRepository = new BookingRepository(bookingFile);
        showtimeRepository = new ShowtimeRepository(showtimeFile);

        validator = new BookingValidator(showtimeRepository, seatRepository);

        priceService = new BookingPriceService();

        manager = new BookingManager(
                bookingRepository,
                new RegularBookingFactory(),
                priceService
        );

        bookingService = new BookingService(
                bookingRepository,
                showtimeRepository,
                seatRepository,
                validator,
                manager
        );

    }

    @Test
    void testBookSeatSuccess() {
        Booking booking = bookingService.bookSeat(1, 5, "Charlie");

        assertNotNull(booking);
        assertEquals(1, booking.getShowtimeId());
        assertEquals(5, booking.getSeatId());
        assertEquals("Charlie", booking.getCustomerName());
    }

    @Test
    void testBookSeatSeatUnavailable() {

        bookingService.bookSeat(1, 5, "Charlie");

        assertThrows(SeatUnavailableException.class, () -> bookingService.bookSeat(1, 5, "DoMixi"));
    }

    @Test
    void testBookSeatInvalidInput() {
        assertThrows(InvalidInputException.class, () -> bookingService.bookSeat(999, 1, "Charlie"));
    }

    @Test
    void testGetAvailableSeats() {

        List<Seat> seats = bookingService.getAvailableSeats(1);
        assertEquals(14, seats.size());

        assertFalse(seats.stream()
                .anyMatch(s -> s.getId() == 1));

        assertFalse(seats.stream()
                .anyMatch(s -> s.getId() == 2));
    }

    @Test
    void testFlushBookings() {

        bookingService.bookSeat(1, 5, "Charlie");

        bookingService.flushBookings();

        assertTrue(bookingRepository.isSeatBooked(1, 5));
    }

    @Test
    void testVIPBooking() {

        BookingManager vipManager = new BookingManager(
                bookingRepository,
                new VIPBookingFactory(2, 20),
                priceService
        );

        BookingService vipService = new BookingService(
                bookingRepository,
                showtimeRepository,
                seatRepository,
                validator,
                vipManager
        );

        Booking booking = vipService.bookSeat(1, 6, "VIP User");

        assertEquals(2, booking.getVipLevel());
        assertEquals(20, booking.getDiscount());
    }
}
