package com.cinema.repository;

import com.cinema.model.Showtime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

public class ShowtimeRepositoryTest {

    private ShowtimeRepository repository;

    @TempDir
    Path tempDir;
    String showtimeFile;

    @BeforeEach
    void setUp() {
        showtimeFile = tempDir.resolve("showtimes.csv").toString();
        repository = new ShowtimeRepository(showtimeFile);

        repository.clear();
        repository.data.add(new Showtime(1, 2, 3, "2026-01-01 12:00"));
        repository.data.add(new Showtime(2, 3, 4, "2026-01-02 09:00"));
        repository.data.add(new Showtime(3, 3, 2, "2026-03-01 10:00"));
        repository.data.add(new Showtime(4, 3, 4, "2026-01-04 11:00"));
        repository.data.add(new Showtime(5, 5, 3, "2026-05-01 12:10"));
    }

    @Test
    void testLoadFromFile() {
        assertEquals(5, repository.findAll().size());
    }

    @Test
    void testFindById() {
        Showtime showtime = repository.findById(2);
        assertEquals(3, showtime.getMovieId());
        assertEquals(4, showtime.getTheaterId());
        assertEquals("2026-01-02 09:00", showtime.getStartTime());
    }

    @Test
    void testSaveNew() {
        Showtime showtime = new Showtime(6, 6, 6, "2026-06-06 06:06");
        repository.save(showtime);
        assertNotNull(repository.findById(6));
        assertEquals(6, repository.findAll().size());
    }

    @Test
    void updateExisting() {
        Showtime updated = new Showtime(2, 4, 4, "2026-02-02 12:12");
        repository.save(updated);
        Showtime found =  repository.findById(2);
        assertEquals(4, found.getMovieId());
        assertEquals(4, found.getTheaterId());
        assertEquals("2026-02-02 12:12", found.getStartTime());
    }

    @Test
    void testDelete() {
        repository.delete(4);
        assertNull(repository.findById(4));
        assertEquals(4, repository.findAll().size());
    }

    @Test
    void testFindByMovieId() {
        List<Showtime> showtimes = repository.findByMovieId(3);
        assertEquals(3, showtimes.size());
        showtimes = repository.findByMovieId(4);
        assertEquals(0, showtimes.size());
    }

    @Test
    void testFindByTheaterId() {
        List<Showtime> showtimes = repository.findByTheaterId(2);
        assertEquals(1, showtimes.size());
        showtimes = repository.findByTheaterId(4);
        assertEquals(2, showtimes.size());
    }
}
