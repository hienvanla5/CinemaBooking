package com.cinema.simulation;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.service.BookingService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BookingSimulation {

    private final BookingService bookingService;
    private final ExecutorService executor;

    public BookingSimulation(BookingService bookingService) {
        this.bookingService = bookingService;
        this.executor = Executors.newFixedThreadPool(10);
    }

    public SimulationResult simulateConcurrentBooking(int showtimeId, int seatId, int numberOfUsers) {
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        for (int i = 0; i < numberOfUsers; i++) {
            final int userId = i + 1;
            executor.submit(() -> {
                try {
                    Booking booking = bookingService.bookSeat(showtimeId, seatId, "User-" + userId);
                    successCount.incrementAndGet();
                    System.out.println("✅ User-" + userId + " successfully books seat " + seatId);
                } catch (SeatUnavailableException e) {
                    failureCount.incrementAndGet();
                    System.out.println("❌ User-" + userId + " failed books " + e.getMessage());
                } catch (InvalidInputException e) {
                    failureCount.incrementAndGet();
                    System.out.println("⚠️ User-" + userId + " error input data: " + e.getMessage());
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    System.out.println("💥 User-" + userId + " unexpected error: " + e.getMessage());
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
