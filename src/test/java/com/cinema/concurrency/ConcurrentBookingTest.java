package com.cinema.concurrency;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Seat;
import com.cinema.repository.*;
import com.cinema.service.BookingService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentBookingTest {

    @TempDir
    Path tempDir;

    private BookingService bookingService;
    private BookingRepository bookingRepository;
    private SeatRepository seatRepository;
    private ShowtimeRepository showtimeRepository;

    @BeforeEach
    void setUp() throws IOException {

        String movieFile = tempDir.resolve("movies.csv").toString();
        String theaterFile = tempDir.resolve("theaters.csv").toString();
        String seatFile = tempDir.resolve("seats.csv").toString();
        String showtimeFile =  tempDir.resolve("showtimes.csv").toString();
        String bookingFile = tempDir.resolve("bookings.csv").toString();

        FileStorage fs = new FileStorage();

        fs.writeLines(movieFile, List.of("1|Avengers|180"));

        fs.writeLines(theaterFile, List.of("1|Hall A|5|5"));

        seatRepository = new SeatRepository(seatFile);
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                int id = row * 5 + col + 1;
                seatRepository.save(new Seat(id, 1, row, col));
            }
        }

        fs.writeLines(showtimeFile, List.of("1|1|1|2026-07-15 10:00"));

        fs.writeLines(bookingFile, List.of());

        MovieRepository movieRepo = new MovieRepository(movieFile);
        TheaterRepository theaterRepo = new TheaterRepository(theaterFile);
        showtimeRepository = new ShowtimeRepository(showtimeFile);
        bookingRepository = new BookingRepository(bookingFile);

        bookingService = new BookingService(bookingRepository, movieRepo, showtimeRepository, seatRepository);
    }

    @Test
    void testConcurrentBooking_SameSeat_OnlyOneSucceeds() throws InterruptedException {
        int numberOfThreads = 10;
        int showtimeId = 1;
        int seatId = 5;

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numberOfThreads);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        for (int i = 0; i < numberOfThreads; i++) {
            final int userId = i + 1;
            new Thread(() -> {
                try {
                    startLatch.await();
                    bookingService.bookSeat(showtimeId, seatId, "User-" + userId);
                    successCount.incrementAndGet();
                    System.out.println("✅ User-" + userId + " booked seat " + seatId + " success.");
                } catch (SeatUnavailableException e) {
                    failureCount.incrementAndGet();
                    System.out.println("❌ User-" + userId + "failed (seat already booked).");
                } catch (InvalidInputException e) {
                    failureCount.incrementAndGet();
                    System.out.println("⚠️ User-" + userId + " error data: " + e.getMessage());
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    System.out.println("💥 User-" + userId + " error: " + e.getMessage());
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();

        endLatch.await();

        bookingService.flushBookings();
        assertTrue(bookingRepository.isSeatBooked(showtimeId, seatId));
        assertEquals(1, successCount.get(), "Only 1 user booked successfully");
        assertEquals(numberOfThreads - 1,  failureCount.get(), "9 users failed books");
    }

    @Test
    void testConcurrentBooking_DifferentSeats_AllSucceed() throws InterruptedException {

        int numberOfThreads = 10;
        int showtimeId = 1;
        int[] seatIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numberOfThreads);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        for (int i = 0; i < numberOfThreads; i++) {
            final int userId = i + 1;
            final int seatId = seatIds[i];
            new Thread(() -> {
               try {
                   startLatch.await();
                   bookingService.bookSeat(showtimeId, seatId, "User-" + userId);
                   successCount.incrementAndGet();
                   System.out.println("✅ User-" + userId + " booked seat " + seatId + " success.");
               } catch (SeatUnavailableException e) {
                   failureCount.incrementAndGet();
                   System.out.println("❌ User-" + userId + "failed (seat already booked).");
               } catch (InvalidInputException e) {
                   failureCount.incrementAndGet();
                   System.out.println("⚠️ User-" + userId + " error data: " + e.getMessage());
               } catch (Exception e) {
                   failureCount.incrementAndGet();
                   System.out.println("💥 User-" + userId + " error: " + e.getMessage());
               } finally {
                   endLatch.countDown();
               }
            }).start();
        }

        startLatch.countDown();
        endLatch.await();

        bookingService.flushBookings();

        assertEquals(numberOfThreads, successCount.get(), "All 10 users booked successfully");
        assertEquals(0, failureCount.get(), "No such user failed booked");

        List<Integer> bookedSeats = bookingRepository.getBookedSeatsByShowtime(showtimeId);
        assertEquals(numberOfThreads, bookedSeats.size(), "Number of seats match with number of users");
        for (int seatId : seatIds) {
            assertTrue(bookedSeats.contains(seatId), "Booked seat " + seatId + " success.");
        }
    }
}
