package com.cinema.concurrency;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Seat;
import com.cinema.repository.*;
import com.cinema.service.BookingService;
import static org.junit.jupiter.api.Assertions.*;

import com.cinema.simulation.BookingSimulation;
import com.cinema.util.FileStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
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

        FileStorage.getInstance().writeLines(movieFile, List.of("1|Avengers|180"));

        FileStorage.getInstance().writeLines(theaterFile, List.of("1|Hall A|5|10"));

        seatRepository = new SeatRepository(seatFile);
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                int id = row * 10 + col + 1;
                seatRepository.save(new Seat(id, 1, row, col));
            }
        }

        FileStorage.getInstance().writeLines(showtimeFile, List.of("1|1|1|2026-07-15 10:00"));

        FileStorage.getInstance().writeLines(bookingFile, List.of());

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

    @Test
    void testHighConcurrency_100Users_50Seats() throws InterruptedException {

        int numberOfUsers = 100;
        int maxSeats = 50;
        int showtimeId = 1;

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numberOfUsers);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        for (int i = 0; i < numberOfUsers; i++) {
            final int userId = i + 1;
            final int seatId = (userId % maxSeats) + 1;
            new Thread(() -> {
                try {
                    startLatch.await();
                    bookingService.bookSeat(showtimeId, seatId, "User-" + userId);
                    successCount.incrementAndGet();
                } catch (SeatUnavailableException e) {
                    failureCount.incrementAndGet();
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();
        endLatch.await();
        bookingService.flushBookings();

        List<Integer> bookedSeats = bookingRepository.getBookedSeatsByShowtime(showtimeId);
        assertEquals(maxSeats, bookedSeats.size(), "All 50 seats are booked successfully");
        assertEquals(numberOfUsers - maxSeats, failureCount.get(), "50 user booked fail");
        assertEquals(maxSeats, successCount.get(), "50 user booked successfully");
    }

    @Test
    void testMixedScenarios() throws InterruptedException {
        int showtimeId = 1;
        int validSeat1 = 5;
        int validSeat2 = 6;
        int invalidSeat = 999;
        int invalidShowtime = 999;

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(5);

        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger invalidInput = new AtomicInteger(0);
        AtomicInteger seatUnavailable = new AtomicInteger(0);

        // valid -> success
        new Thread(() ->
            runBooking(showtimeId, validSeat1, "User1", startLatch, endLatch, success, invalidInput, seatUnavailable)
        ).start();

        // booking same seatId1 ->  SeatUnavailable
        new Thread(() ->
            runBooking(showtimeId, validSeat1, "User2", startLatch, endLatch, success, invalidInput, seatUnavailable)
        ).start();;

        //  seat not exist -> InvalidInputException
        new Thread(() ->
            runBooking(showtimeId, invalidSeat, "User3", startLatch, endLatch, success, invalidInput, seatUnavailable)
        ).start();;

        // showtime not exist -> InvalidInputException
        new Thread(() ->
            runBooking(invalidShowtime, 1, "User4", startLatch, endLatch, success, invalidInput, seatUnavailable)
        ).start();;

        // booking different seat -> success
        new Thread(() -> {
            runBooking(showtimeId, validSeat2, "User5", startLatch, endLatch, success, invalidInput, seatUnavailable);
        }).start();;

        startLatch.countDown();
        endLatch.await();
        bookingService.flushBookings();

        assertEquals(2, success.get(), "All 2 users booked successfully (seat number 5 and 6)");
        assertEquals(1, seatUnavailable.get(), "1 user failed due to booked seat");
        assertEquals(2, invalidInput.get(), "2 user error data (wrong seatId, wrong showtimeId)");
    }

    private void runBooking(int showtimeId, int seatId, String user, CountDownLatch startLatch, CountDownLatch endLatch, AtomicInteger success, AtomicInteger invalidInput, AtomicInteger seatUnavailable) {
        try {
            startLatch.await();
            bookingService.bookSeat(showtimeId, seatId, user);
            success.incrementAndGet();
        } catch (InvalidInputException e) {
            invalidInput.incrementAndGet();
        } catch (SeatUnavailableException e) {
            seatUnavailable.incrementAndGet();
        } catch (Exception e) {

        } finally {
            endLatch.countDown();
        }
    }

    @Test
    void testWithRealFile() throws InterruptedException, IOException {
        String realMovieFile = "src/test/resources/data/movies.csv";
        String realTheaterFile = "src/test/resources/data/theaters.csv";
        String realSeatFile =  "src/test/resources/data/seats.csv";
        String realShowtimeFile =  "src/test/resources/data/showtimes.csv";
        String realBookingFile = "src/test/resources/data/bookings.csv";

        FileStorage.getInstance().writeLines(realMovieFile, List.of("1|Avengers|180"));

        FileStorage.getInstance().writeLines(realTheaterFile, List.of("1|Hall A|5|10"));

        seatRepository = new SeatRepository(realSeatFile);
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                int id = row * 10 + col + 1;
                seatRepository.save(new Seat(id, 1, row, col));
            }
        }

        FileStorage.getInstance().writeLines(realShowtimeFile, List.of("1|1|1|2026-07-15 10:00"));

        FileStorage.getInstance().writeLines(realBookingFile, List.of());

        MovieRepository movieRepo = new MovieRepository(realMovieFile);
        TheaterRepository theaterRepo = new TheaterRepository(realTheaterFile);
        showtimeRepository = new ShowtimeRepository(realShowtimeFile);
        bookingRepository = new BookingRepository(realBookingFile);

        bookingService = new BookingService(bookingRepository, movieRepo, showtimeRepository, seatRepository);

        //
        BookingSimulation simulation = new BookingSimulation(bookingService);
        int showtimeId = 1;
        int seatId = 5;
        int numberOfUsers = 10;
        BookingSimulation.SimulationResult result = simulation.simulateConcurrentBooking(showtimeId, seatId, numberOfUsers);

        assertEquals(1, result.getSuccess());
        assertEquals(9, result.getFailure());

        assertTrue(new File(realBookingFile).exists());
        List<String> lines = FileStorage.getInstance().readLines(realBookingFile);
        assertTrue(lines.stream().anyMatch(line -> line.contains("5")));
    }
}
