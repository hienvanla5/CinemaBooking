package com.cinema.repository;

import com.cinema.model.Seat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SeatRepositoryTest {

    private SeatRepository repository;

    @TempDir
    Path tempDir;
    private String seatFile;

    @BeforeEach
    void setUp() throws IOException {
        seatFile = tempDir.resolve("seats.csv").toString();
        repository = new SeatRepository(seatFile);

        repository.clear();
        repository.saveToFile();
    }

    @Test
    void testLoadFromFile() {
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void testSaveAndFind() {
        Seat seat = new Seat(1, 1, 1, 1);
        repository.save(seat);
        Seat found = repository.findById(1);
        assertNotNull(found);
        assertEquals(1, found.getTheaterId());
        assertEquals(1, found.getRow());
        assertEquals(1, found.getColumn());
    }

    @Test
    void testFindById() {
        Seat seat = new Seat(1, 1, 1, 1);
        Seat found = repository.findById(1);
        assertEquals(1, seat.getId());
        assertEquals(1, seat.getTheaterId());
        assertEquals(1, seat.getRow());
        assertEquals(1, seat.getColumn());
    }

    @Test
    void updateExisting() {
        Seat updated = new Seat(1, 2, 40, 30);
        repository.save(updated);
        Seat found = repository.findById(1);
        assertEquals(2, found.getTheaterId());
        assertEquals(40, found.getRow());
        assertEquals(30, found.getColumn());
    }

    @Test
    void testDelete() {
        repository.save(new Seat(1, 1, 1, 1));
        repository.save(new Seat(2, 1, 1, 2));
        repository.save(new Seat(3, 2, 1, 1));
        repository.delete(1);
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void testFindByTheaterId() {
        repository.save(new Seat(1, 1, 1, 1));
        repository.save(new Seat(2, 1, 1, 2));
        repository.save(new Seat(3, 2, 1, 1));

        List<Seat> seats = repository.findByTheaterId(1);
        assertEquals(2, seats.size());

        seats = repository.findByTheaterId(2);
        assertEquals(1, seats.size());
    }

    @Test
    void testDeleteByTheaterId() {
        repository.save(new Seat(1, 1, 1, 1));
        repository.save(new Seat(2, 1, 1, 2));
        repository.deleteByTheaterId(1);
        List<Seat> seats = repository.findByTheaterId(1);
        assertTrue(seats.isEmpty());
    }
}
