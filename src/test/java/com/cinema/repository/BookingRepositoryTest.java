package com.cinema.repository;

import com.cinema.model.Booking;
import com.cinema.util.FileStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingRepositoryTest {

    private BookingRepository repository;

    @TempDir
    Path tempDir;
    private String bookingsFile;

    @BeforeEach
    void setUp() throws IOException {
        bookingsFile = tempDir.resolve("bookings.csv").toString();

        List<String> lines = Arrays.asList(
                "1|5|Alice|2026-07-08 09:00:00",
                "1|10|Bob|2026-07-08 09:05:00",
                "2|3|Charlie|2026-07-08 09:10:00"
        );
        FileStorage.getInstance().writeLines(bookingsFile, lines);
        repository = new BookingRepository(bookingsFile);
    }

    @Test
    void testLoadFromFile() {
        assertEquals(3, repository.findAll().size());
        assertTrue(repository.isSeatBooked(1, 5));
    }

    @Test
    void testSave() {
        Booking newBooking = new Booking(3, 7, "David");
        repository.save(newBooking);
        assertEquals(4, repository.findAll().size());
        assertTrue(repository.isSeatBooked(3, 7));
    }

    @Test
    void testIsSeatBooked() {
        assertTrue(repository.isSeatBooked(1, 5));
        assertTrue(repository.isSeatBooked(1, 10));
        assertTrue(repository.isSeatBooked(2, 3));
        assertFalse(repository.isSeatBooked(1, 7));
        assertFalse(repository.isSeatBooked(3, 1));
    }

    @Test
    void testFindByShowtimeId() {
        List<Booking> bookings = repository.findByShowtimeId(1);
        assertEquals(2, bookings.size());
        bookings = repository.findByShowtimeId(2);
        assertEquals(1, bookings.size());
        bookings = repository.findByShowtimeId(3);
        assertTrue(bookings.isEmpty());
    }

    @Test
    void testDeleteByShowtimeAndSeat() {
        repository.deleteByShowtimeAndSeat(1, 5);
        assertFalse(repository.isSeatBooked(1, 5));
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void testSaveToFilePersistence() {
        repository.save(new Booking(4, 1, "Eve"));

        BookingRepository newRepo = new BookingRepository();
        assertEquals(4, repository.findAll().size());
        assertTrue(repository.isSeatBooked(4, 1));
    }

    @Test
    void testGetBookedSeatsByShowtime() {
        List<Integer> bookedSeats = repository.getBookedSeatsByShowtime(1);
        assertEquals(2, bookedSeats.size());
        assertTrue(bookedSeats.contains(5));
        assertTrue(bookedSeats.contains(10));

        bookedSeats = repository.getBookedSeatsByShowtime(2);
        assertEquals(1, bookedSeats.size());
        assertTrue(bookedSeats.contains(3));

        bookedSeats = repository.getBookedSeatsByShowtime(3);
        assertTrue(bookedSeats.isEmpty());
    }

    @Test
    void testSaveAll() {
        List<Booking> bookings = new ArrayList<>();
        Booking newBooking1 = new Booking(3, 7, "David");
        Booking newBooking2 = new Booking(3, 8, "David");
        Booking newBooking3 = new Booking(3, 9, "David");
        bookings.add(newBooking1);
        bookings.add(newBooking2);
        bookings.add(newBooking3);
        repository.saveAll(bookings);
        assertEquals(6, repository.findAll().size());
        assertTrue(repository.isSeatBooked(3, 7));
    }
}
