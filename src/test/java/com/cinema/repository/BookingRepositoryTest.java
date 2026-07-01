package com.cinema.repository;

import com.cinema.model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingRepositoryTest {

    private BookingRepository repository;

    @TempDir
    Path tempDir;
    private String bookingFile;

    @BeforeEach
    void setUp() {
        bookingFile = tempDir.resolve("bookings.csv").toString();
        repository = new BookingRepository(bookingFile);

        repository.data.clear();
        repository.data.add(new Booking(1, 5, "Alice"));
        repository.data.add(new Booking(1, 10, "Bob"));
        repository.data.add(new Booking(2, 3, "Charlie"));
        repository.saveToFile();
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
    void testFindByMovieId() {
        assertEquals(2, repository.findByMovieId(1).size());
        assertEquals(1, repository.findByMovieId(2).size());
        assertEquals(0, repository.findByMovieId(3).size());
    }

    @Test
    void testDeleteByMovieAndSeat() {
        repository.deleteByMovieAndSeat(1, 5);
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
    void testGetBookedSeats() {
        List<Integer> bookedSeats = repository.getBookedSeats(1);
        assertEquals(2, bookedSeats.size());
        assertTrue(bookedSeats.contains(5));
        assertFalse(bookedSeats.contains(3));

        bookedSeats = repository.getBookedSeats(2);
        assertEquals(1, bookedSeats.size());
        assertTrue(bookedSeats.contains(3));
    }
}
