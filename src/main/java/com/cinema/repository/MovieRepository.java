package com.cinema.repository;

import com.cinema.model.Movie;

public class MovieRepository extends BaseRepository<Movie> {

    @Override
    public Movie findById(int id) {
        return data.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Movie movie) {
        Movie existing = findById(movie.getId());
        if (existing != null) {
            data.remove(existing);
            data.add(movie);
        } else {
            data.add(movie);
        }
    }

    @Override
    public void delete(int id) {
        data.removeIf(m -> m.getId() == id);
    }
}
