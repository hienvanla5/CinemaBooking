package com.cinema.repository;

import com.cinema.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovieRepositoryTest {

    private MovieRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MovieRepository();

        repository.save(new Movie(1, "Avengers", 180));
        repository.save(new Movie(2, "Titanic", 195));
        repository.save(new Movie(3, "Inception", 148));
    }

    @Test
    void testFindAll() {
        assertEquals(3, repository.findAll().size());
    }

    @Test
    void testFindById() {
        Movie movie = repository.findById(2);
        assertNotNull(movie);
        assertEquals("Titanic", movie.getTitle());
    }

    @Test
    void testSaveNew() {
        Movie newMovie = new Movie(4, "Interstellar", 169);
        repository.save(newMovie);
        assertEquals(4, repository.findAll().size());
        assertNotNull(repository.findById(4));
    }

    @Test
    void updateExisting() {
        Movie updated = new Movie(3, "Inception (2010)", 148);
        repository.save(updated);
        Movie found = repository.findById(3);
        assertEquals("Inception (2010)", found.getTitle());
    }

    @Test
    void testDelete() {
        repository.delete(1);
        assertNull(repository.findById(1));
        assertEquals(2, repository.findAll().size());
    }
}
