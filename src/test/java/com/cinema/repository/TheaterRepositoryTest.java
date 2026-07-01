package com.cinema.repository;

import com.cinema.model.Theater;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

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
        repository.data.add(new Theater(1, "Hall A", 10, 10));
        repository.data.add(new Theater(2, "Hall B", 20, 25));
        repository.saveToFile();
    }

    @Test
    void testLoadFromFile() {
        assertEquals(2, repository.findAll().size());
        assertEquals("Hall B", repository.findById(2).getName());
    }

    @Test
    void testFindById() {
        Theater theater = repository.findById(2);
        assertEquals("Hall B", theater.getName());
        assertEquals(20, theater.getTotalRows());
        assertEquals(25, theater.getTotalColumns());
    }

    @Test
    void testSaveNew() {
        Theater theater = new Theater(3, "Hall C", 50, 50);
        repository.save(theater);
        assertNotNull(repository.findById(3));
        assertEquals(3, repository.findAll().size());
        assertEquals("Hall C", repository.findById(3).getName());
        assertEquals(50, repository.findById(3).getTotalRows());
        assertEquals(50, repository.findById(3).getTotalColumns());
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
        repository.delete(1);
        assertNull(repository.findById(1));
        assertEquals(1, repository.findAll().size());
    }


}
