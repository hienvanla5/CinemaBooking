package com.cinema.repository;

import com.cinema.model.Seat;
import com.cinema.model.Theater;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

public class TheaterRepositoryTest {

    private TheaterRepository repository;

    @TempDir
    Path tempDir;
    private String theaterFile;

    @BeforeEach
    void setUp() {
        theaterFile = tempDir.resolve("theaters.csv").toString();
        repository = new TheaterRepository(theaterFile);

        repository.clear();
        repository.saveToFile();
    }

    @Test
    void testLoadFromFile() {Theater theater1 = new Theater(1, "Hall A", 2, 3);
        Theater theater2 = new Theater(2, "Hall B", 2, 3);
        Theater theater3 = new Theater(3, "Hall C", 3, 4);
        repository.save(theater1);
        repository.save(theater2);
        repository.save(theater3);
        assertEquals(3, repository.findAll().size());
        assertEquals("Hall B", repository.findById(2).getName());
    }

    @Test
    void testFindById() {
        Theater theater = new Theater(2, "Hall B", 1, 2);
        repository.save(theater);

        Theater found = repository.findById(2);
        assertEquals("Hall B", found.getName());
        assertEquals(1, found.getTotalRows());
        assertEquals(2, found.getTotalColumns());
    }

    @Test
    void testSaveNew() {
        Theater theater1 = new Theater(1, "Hall A", 2, 3);
        Theater theater2 = new Theater(2, "Hall B", 2, 3);
        Theater theater3 = new Theater(3, "Hall C", 3, 4);
        repository.save(theater1);
        repository.save(theater2);
        repository.save(theater3);
        assertNotNull(repository.findById(3));
        assertEquals(3, repository.findAll().size());
        assertEquals("Hall C", repository.findById(3).getName());
        assertEquals(3, repository.findById(3).getTotalRows());
        assertEquals(4, repository.findById(3).getTotalColumns());
    }

    @Test
    void updateExisting() {
        Theater theater = new Theater(2, "Hall B (Updated)", 20, 25);
        repository.save(theater);
        Theater found = repository.findById(2);
        assertEquals("Hall B (Updated)", found.getName());
    }

    @Test
    void testDelete() {
        Theater theater1 = new Theater(1, "Hall A", 2, 3);
        Theater theater2 = new Theater(2, "Hall B", 2, 3);
        repository.save(theater1);
        repository.save(theater2);
        repository.delete(1);
        assertNull(repository.findById(1));
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testInitializeSeatsWhenAddingNewTheater() {
        TheaterRepository theaterRepo = new TheaterRepository(theaterFile);
        Theater theater = new Theater(1, "Hall A", 2, 3);
        theaterRepo.save(theater);

        SeatRepository seatRepo = new SeatRepository(
                theaterFile.replace("theaters.csv", "seats.csv")
        );
        List<Seat> seats = seatRepo.findByTheaterId(1);
        assertEquals(6, seats.size());
        Seat firstSeat = seats.get(0);
        assertEquals(1, firstSeat.getRow());
        assertEquals(1, firstSeat.getColumn());
    }
}
