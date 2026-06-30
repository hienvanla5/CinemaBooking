package com.cinema.repository;

import com.cinema.model.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository extends BaseRepository<Movie> {

    private String filePath;
    private final FileStorage fileStorage;

    public MovieRepository() {
        this("src/main/resources/data/movies.csv");
    }

    public MovieRepository(String filePath) {
        this.filePath = filePath;
        this.fileStorage = new FileStorage();
        loadFromFile();
        createSampleDataIfEmpty();
    }

    private void loadFromFile() {
        try {
            List<String> lines = fileStorage.readLines(filePath);
            data = new ArrayList<>();

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    int duration = Integer.parseInt(parts[2].trim());
                    data.add(new Movie(id, title, duration));
                } else {
                    System.err.println("⚠️ Line is not formatted correctly");
                }
            }
            System.out.println("✅ Downloaded " + data.size() + " movies in file.");
        } catch (IOException e) {
            System.out.println("📂 File is not exist, init a empty list");
            data = new ArrayList<>();
        } catch (NumberFormatException e) {
            System.err.println("⚠️ Error parse number, pass line: " + e.getMessage());
            data = new ArrayList<>();
        }
    }

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
        }
        data.add(movie);
        saveToFile();
    }

    @Override
    public void delete(int id) {
        boolean removed = data.removeIf(m -> m.getId() == id);
        if (removed) {
            saveToFile();
        }
    }

    public void saveToFile() {
        try {
            List<String> lines = new ArrayList<>();
            for (Movie movie : data) {
                String line = movie.getId() + "|" + movie.getTitle() + "|" + movie.getDuration();
                lines.add(line);
            }
            fileStorage.writeLines(filePath, lines);
            System.out.println("📂 Saved " + data.size() + " movies in file.");
        } catch (IOException e) {
            System.err.println("❌ Error when write file: " + e.getMessage());
        }
    }

    public void createSampleDataIfEmpty() {
        if (data.isEmpty()) {
            data.add(new Movie(1, "Avengers: Endgame", 180));
            data.add(new Movie(2, "Titanic", 195));
            data.add(new Movie(3, "Inception", 148));
            saveToFile();
            System.out.println("✅ Created samples data.");
        }
    }
}
