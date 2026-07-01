package com.cinema.repository;

import com.cinema.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovieRepositoryTest {

    private MovieRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MovieRepository("src/test/resources/data/movies.csv");

        repository.clear();
        repository.data.add(new Movie(1, "Avengers: Endgame", 120));
        repository.data.add(new Movie(2, "Titanic", 150));
        repository.saveToFile();
    }

    @Test
    void testLoadFromFile() {
        assertEquals(2, repository.findAll().size());
        assertEquals("Avengers: Endgame", repository.findById(1).getTitle());
    }

    @Test
    void testFindAll() {
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void testFindById() {
        Movie movie = repository.findById(2);
        assertNotNull(movie);
        assertEquals("Titanic", movie.getTitle());
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

        assertNotNull(repository.findById(4));
        assertEquals(3, repository.findAll().size());

    }

    @Test void testFindByName() {
        Movie movie = repository.findByName("avengers");
        assertNotNull(movie);
        assertEquals("Avengers: Endgame", movie.getTitle());

        movie = repository.findByName("titanic");
        assertNotNull(movie);
        assertEquals("Titanic", movie.getTitle());

        movie = repository.findByName("inception");
        assertNull(movie);

        movie = repository.findByName("");
        assertNull(movie);
    }
}
