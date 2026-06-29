package com.cinema.repository;

import com.cinema.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovieRepositoryTest {

    private MovieRepository repository;
    private static final String TEST_FILE = "src/test/resources/tests_movies.csv";

    @BeforeEach
    void setUp() {
        repository = new MovieRepository();

        repository.data.clear();
        repository.data.add(new Movie(1, "Test Movie 1", 120));
        repository.data.add(new Movie(2, "Test Movie 2", 150));
        repository.saveToFile();
    }

    @Test
    void testLoadFromFile() {
        MovieRepository newRepo = new MovieRepository();
        assertEquals(2, repository.findAll().size());
        assertNotNull(newRepo.findById(1));
        assertEquals("Test Movie 1", newRepo.findById(1).getTitle());
    }

    @Test
    void testFindAll() {
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void testFindById() {
        Movie movie = repository.findById(1);
        assertNotNull(movie);
        assertEquals("Test Movie 1", movie.getTitle());
    }

    @Test
    void testSaveNew() {
        Movie newMovie = new Movie(3, "Test Movie 3", 180);
        repository.save(newMovie);
        assertEquals(3, repository.findAll().size());
        assertNotNull(repository.findById(3));
    }

    @Test
    void updateExisting() {
        Movie updated = new Movie(2, "Test Movie 2 (Updated)", 160);
        repository.save(updated);
        Movie found = repository.findById(2);
        assertEquals("Test Movie 2 (Updated)", found.getTitle());
    }

    @Test
    void testDelete() {
        repository.delete(1);
        assertNull(repository.findById(1));
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testSaveToFilePersistence() {
        repository.save(new Movie(4, "Test Movie 4", 200));

        MovieRepository newRepo = new MovieRepository();
        assertNotNull(newRepo.findById(4));
        assertEquals(3, newRepo.findAll().size());

    }
}
