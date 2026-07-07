package com.cinema.simulation;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.service.BookingService;
import com.cinema.util.AppConstants;
import com.cinema.util.AppLogger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simulates concurrent ticket booking requests.
 * <p>
 * This class creates multiple threads that attempt to book the same seat
 * simultaneously. It is primarily used for testing the thread safety of
 * the booking system and verifying that only one booking succeeds for
 * a particular seat.
 */
public class BookingSimulation {

    private final BookingService bookingService;
    private final ExecutorService executor;
    private static final AppLogger logger = AppLogger.getInstance();

    /**
     * Creates a booking simulation.
     *
     * @param bookingService the booking service used to process booking requests
     */
    public BookingSimulation(BookingService bookingService) {
        this.bookingService = bookingService;
        this.executor = Executors.newFixedThreadPool(AppConstants.DEFAULT_THREAD_POOL_SIZE);
    }

    /**
     * Simulates multiple users attempting to book the same seat concurrently.
     * <p>
     * A fixed-size thread pool is used to execute booking requests in parallel.
     * After all tasks complete (or the timeout is reached), all pending bookings
     * are flushed to persistent storage.
     *
     * @param showtimeId the ID of the target showtime
     * @param seatId the ID of the seat to be booked
     * @param numberOfUsers the number of concurrent users participating in the simulation
     * @return the simulation result containing the number of successful and failed bookings
     */
    public SimulationResult simulateConcurrentBooking(int showtimeId, int seatId, int numberOfUsers) {
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        for (int i = 0; i < numberOfUsers; i++) {
            final int userId = i + 1;

            executor.submit(() -> {
                try {
                    Booking booking = bookingService.bookSeat(showtimeId, seatId, "User-" + userId);

                    successCount.incrementAndGet();
                    logger.logInfo("✅ User-" + userId + " successfully booked seat " + seatId);

                } catch (SeatUnavailableException e) {
                    failureCount.incrementAndGet();
                    logger.logWarning("❌ User-" + userId + " failed to book the seat: " + e.getMessage());

                } catch (InvalidInputException e) {
                    failureCount.incrementAndGet();
                    logger.logWarning("⚠️ User-" + userId + " provided invalid input: " + e.getMessage());

                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    logger.logError("💥 User-" + userId + " encountered an unexpected error: " + e.getMessage());
                }
            });
        }

        executor.shutdown();

        try {
            boolean finished = executor.awaitTermination(30, TimeUnit.SECONDS);

            if (!finished) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        bookingService.flushBookings();

        return new SimulationResult(successCount.get(), failureCount.get());
    }

    /**
     * Represents the outcome of a booking simulation.
     * <p>
     * It records the number of successful bookings and failed booking attempts.
     */
    public static class SimulationResult {

        private final int success;
        private final int failure;

        public SimulationResult(int success, int failure) {
            this.success = success;
            this.failure = failure;
        }

        public int getSuccess() {
            return success;
        }

        public int getFailure() {
            return failure;
        }

        @Override
        public String toString() {
            return "SimulationResult{" +
                    "success=" + success +
                    ", failure=" + failure +
                    '}';
        }
    }
}