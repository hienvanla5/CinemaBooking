package com.cinema.service;

import com.cinema.exception.InvalidInputException;
import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowtimeRepository;
import com.cinema.repository.TheaterRepository;
import com.cinema.util.FileStorage;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class BookingValidatorTest {

    private BookingValidator validator;
    private SeatRepository seatRepository;
    private ShowtimeRepository showtimeRepository;
    private TheaterRepository theaterRepository;
    private MovieRepository movieRepository;

    @TempDir
    Path tempDir;
    private String seatFile;
    private String showtimeFile;
    private String theaterFile;
    private String movieFile;

    @BeforeEach
    void setUp() throws IOException {
        seatFile = tempDir.resolve("seats.csv").toString();
        showtimeFile = tempDir.resolve("showtimes.csv").toString();
        theaterFile = tempDir.resolve("theaters.csv").toString();
        movieFile = tempDir.resolve("movies.csv").toString();

        FileStorage.getInstance().writeLines(movieFile, List.of("1|Avengers|200"));
        FileStorage.getInstance().writeLines(theaterFile, List.of("2|Hall B|3|3"));
        FileStorage.getInstance().writeLines(showtimeFile, List.of("1|1|2|2026-07-07 15:00"));

        seatRepository = new SeatRepository(seatFile);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int id = r * 3 + c + 1;
                seatRepository.save(new Seat(id, 2, r, c));
            }
        }

        seatRepository = new SeatRepository(seatFile);
        showtimeRepository = new ShowtimeRepository(showtimeFile);

        validator = new BookingValidator(showtimeRepository, seatRepository);
    }

    @Test
    void testValidateBooking_Success() throws Exception {
        Showtime showtime = validator.validateBooking(1, 5, "Alice");

        assertNotNull(showtime);
        assertEquals(1, showtime.getId());
        assertEquals(1, showtime.getMovieId());
        assertEquals(2, showtime.getTheaterId());
        assertEquals("2026-07-07 15:00", showtime.getStartTime());
    }

    @Test
    void testValidateBooking_InvalidCustomer() throws Exception {
        assertThrows(InvalidInputException.class, () ->
                validator.validateBooking(1, 5 , "")
        );
        assertThrows(InvalidInputException.class, () ->
                validator.validateBooking(1, 5 , null)
        );
    }

    @Test
    void testValidateBooking_InvalidShowtime() throws Exception {
        assertThrows(InvalidInputException.class, () ->
                validator.validateBooking(999, 5, "Alice")
        );
    }

    @Test
    void testValidateBooking_InvalidSeat() throws Exception {
        assertThrows(InvalidInputException.class, () ->
                validator.validateBooking(1, 999, "Alice")
        );
    }

    @Test
    void testValidateBooking_SeatNotInTheater() throws Exception {
        assertThrows(InvalidInputException.class, () ->
                validator.validateBooking(1, 10, "Alice")
        );
        // theater 1 is only includes 9 seat 3*3
    }
}
