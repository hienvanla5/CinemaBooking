package com.cinema.simulation;

import com.cinema.factory.RegularBookingFactory;
import com.cinema.model.Seat;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowtimeRepository;
import com.cinema.service.BookingManager;
import com.cinema.service.BookingPriceService;
import com.cinema.service.BookingService;
import com.cinema.service.BookingValidator;
import com.cinema.util.FileStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingSimulationTest {

    @TempDir
    Path tempDir;

    private BookingService bookingService;
    private BookingRepository bookingRepository;
    private SeatRepository seatRepository;

    @BeforeEach
    void setUp() throws IOException {
        String movieFile = tempDir.resolve("movies.csv").toString();
        String theaterFile = tempDir.resolve("theaters.csv").toString();
        String seatFile = tempDir.resolve("seats.csv").toString();
        String showtimeFile = tempDir.resolve("showtimes.csv").toString();
        String bookingFile = tempDir.resolve("bookings.csv").toString();

        FileStorage.getInstance().writeLines(movieFile, List.of("1|Avengers|180"));

        FileStorage.getInstance().writeLines(theaterFile, List.of("1|Hall A|5|5"));

        seatRepository = new SeatRepository(seatFile);
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                int id = row * 5 + col + 1;
                seatRepository.save(new Seat(id, 1, row, col));
            }
        }

        FileStorage.getInstance().writeLines(showtimeFile, List.of("1|1|1|2026-07-03 10:00"));

        FileStorage.getInstance().writeLines(bookingFile, List.of());

        ShowtimeRepository showtimeRepo = new ShowtimeRepository(showtimeFile);
        bookingRepository = new BookingRepository(bookingFile);

        BookingValidator validator = new BookingValidator(showtimeRepo, seatRepository);

        BookingPriceService priceService = new BookingPriceService();

        BookingManager manager = new BookingManager(
                bookingRepository,
                new RegularBookingFactory(),
                priceService,
                false
        );

        bookingService = new BookingService(bookingRepository, showtimeRepo, seatRepository, validator, manager);
    }

    @Test
    void testConcurrentBooking_SameSeat() {
        BookingSimulation simulation = new BookingSimulation(bookingService);
        int showtimeId = 1;
        int seatId = 5;
        int numberOfUsers = 10;

        BookingSimulation.SimulationResult result = simulation.simulateConcurrentBooking(showtimeId, seatId, numberOfUsers);

        System.out.println("Result: " + result);

        assertEquals(1, result.getSuccess());
        assertEquals(9, result.getFailure());

        assertTrue(bookingRepository.isSeatBooked(showtimeId, seatId));
    }
}
