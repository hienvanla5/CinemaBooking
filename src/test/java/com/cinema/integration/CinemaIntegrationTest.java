package com.cinema.integration;

import com.cinema.exception.SeatUnavailableException;
import com.cinema.factory.RegularBookingFactory;
import com.cinema.model.Booking;
import com.cinema.repository.*;
import com.cinema.service.BookingManager;
import com.cinema.service.BookingPriceService;
import com.cinema.service.BookingService;
import com.cinema.service.BookingValidator;
import com.cinema.util.FileStorage;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static com.cinema.util.FileStorage.getInstance;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CinemaIntegrationTest {

    @TempDir
    Path tempDir;

    private String moviesFile;
    private String theatersFile;
    private String seatsFile;
    private String showtimesFile;
    private String bookingsFile;

    private MovieRepository movieRepository;
    private TheaterRepository theaterRepository;
    private SeatRepository seatRepository;
    private ShowtimeRepository showtimeRepository;
    private BookingRepository bookingRepository;
    private BookingService bookingService;

    @BeforeEach
    void setUp() throws IOException {

        moviesFile = tempDir.resolve("movies.csv").toString();
        theatersFile = tempDir.resolve("theaters.csv").toString();
        seatsFile = tempDir.resolve("seats.csv").toString();
        showtimesFile = tempDir.resolve("showtimes.csv").toString();
        bookingsFile = tempDir.resolve("bookings.csv").toString();

        FileStorage.getInstance().writeLines(moviesFile, List.of(
                "1|Avengers: Endgame|180",
                "2|Titanic|195"
        ));

        FileStorage.getInstance().writeLines(theatersFile, List.of(
                "1|Hall A|3|4"
        ));

        StringBuilder seatsData = new StringBuilder();
        for (int row = 0; row < 3; row++) {
            for  (int col = 0; col < 3; col++) {
                int id = row * 4 + col + 1;
                seatsData.append(id).append("|1|").append(row).append("|").append(col).append("\n");
            }
        }
        FileStorage.getInstance().writeLines(seatsFile, List.of(seatsData.toString().split("\n")));

        FileStorage.getInstance().writeLines(showtimesFile, List.of(
                "1|1|1|2026-07-28 14:00",
                "2|2|1|2026-07-28 16:30"
        ));

        FileStorage.getInstance().writeLines(bookingsFile, List.of());

        movieRepository = new MovieRepository(moviesFile);
        theaterRepository = new TheaterRepository(theatersFile);
        seatRepository = new SeatRepository(seatsFile);
        showtimeRepository = new ShowtimeRepository(showtimesFile);
        bookingRepository = new BookingRepository(bookingsFile);


        BookingValidator validator = new BookingValidator(showtimeRepository, seatRepository);

        BookingPriceService priceService = new BookingPriceService();

        BookingManager manager = new BookingManager(
                bookingRepository,
                new RegularBookingFactory(),
                priceService,
                true
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
    void testBookingFlow_Success() throws Exception {

        Booking booking = bookingService.bookSeat(1, 5, "Alice");
        assertNotNull(booking);
        assertEquals(1, booking.getShowtimeId());
        assertEquals(5, booking.getSeatId());
        assertEquals("Alice", booking.getCustomerName());
        assertTrue(booking.getPrice() > 0, "Ticket price must be greater than zero");

        bookingService.flushBookings();

        assertTrue(bookingRepository.isSeatBooked(1, 5), "Seat 5 have already been booked");

        List<Integer> bookedSeats = bookingRepository.getBookedSeatsByShowtime(1);
        assertTrue(bookedSeats.contains(5), "Seat 5 is in the booked list");
        assertEquals(1, bookedSeats.size(), "Only 1 seat is booked");

        List<Booking> customerBookings = bookingRepository.findByCustomerName("Alice");
        assertEquals(1, customerBookings.size());
        assertEquals("Alice", customerBookings.get(0).getCustomerName());
    }

    @Test
    void testBookingFlow_SeatUnavailable() throws Exception {

        Booking firstBooking = bookingService.bookSeat(1, 5, "Alice");
        assertNotNull(firstBooking);
        bookingService.flushBookings();

        assertThrows(SeatUnavailableException.class, () -> {
            bookingService.bookSeat(1, 5, "Bob");
        });

        List<Booking> allBookings = bookingRepository.findAll();
        assertEquals(1, allBookings.size());

        List<Integer> bookedSeats = bookingRepository.getBookedSeatsByShowtime(1);
        assertEquals(1, bookedSeats.size());
        assertTrue(bookedSeats.contains(5));
    }

    @Test
    void testConcurrentBooking_Integration() throws InterruptedException {
        int numberOfThreads = 10;
        int showtimeId = 1;
        int seatId = 5;

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numberOfThreads);

        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger failure = new AtomicInteger(0);

        for (int i = 0; i < numberOfThreads; i++) {
            final int userId = i + 1;
            new Thread(() -> {
                try {
                    startLatch.await();
                    bookingService.bookSeat(showtimeId, seatId, "User-" + userId);
                    success.incrementAndGet();
                } catch (SeatUnavailableException e) {
                    failure.incrementAndGet();
                } catch (Exception e) {
                    failure.incrementAndGet();
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();
        endLatch.await();

        bookingService.flushBookings();

        assertEquals(1, success.get(), "Only 1 user booked successfully");
        assertEquals(numberOfThreads - 1, failure.get(), "9 users booked fail");

        assertTrue(bookingRepository.isSeatBooked(showtimeId, seatId));

        BookingRepository newRepo = new BookingRepository(bookingsFile);
        assertTrue(newRepo.isSeatBooked(showtimeId, seatId));
        assertEquals(1, newRepo.getBookedSeatsByShowtime(showtimeId).size());
    }
}
