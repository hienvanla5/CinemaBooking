package com.cinema.simulation;

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

public class BookingSimulationTest {

    @TempDir
    Path tempDir;

    private BookingService bookingService;
    private BookingRepository  bookingRepository;
    private SeatRepository seatRepository;

    @BeforeEach
    void setUp() throws IOException {
        String movieFile = tempDir.resolve("movies.csv").toString();
        String theaterFile = tempDir.resolve("theaters.csv").toString();
        String seatFile = tempDir.resolve("seats.csv").toString();
        String showtimeFile = tempDir.resolve("showtimes.csv").toString();
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

        fs.writeLines(showtimeFile, List.of("1|1|1|2026-07-03 10:00"));

        fs.writeLines(bookingFile, List.of());

        MovieRepository movieRepo = new MovieRepository(movieFile);
        TheaterRepository theaterRepo = new TheaterRepository(theaterFile);
        ShowtimeRepository showtimeRepo = new ShowtimeRepository(showtimeFile);
        bookingRepository = new BookingRepository(bookingFile);

        bookingService = new BookingService(bookingRepository, movieRepo, showtimeRepo, seatRepository);
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
