package com.cinema.repository;

import com.cinema.model.Movie;
import com.cinema.util.AppConstants;
import com.cinema.util.AppLogger;
import com.cinema.util.FileStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing {@link Movie} objects.
 * <p>
 * This repository loads movie data from a CSV file, stores it in memory,
 * and persists any changes back to the file. If the data source is empty,
 * a set of sample movies is created automatically.
 */
public class MovieRepository extends BaseRepository<Movie> {

    private String filePath;
    private static final AppLogger logger = AppLogger.getInstance();

    /**
     * Creates a movie repository using the default movie data file.
     */
    public MovieRepository() {
        this(AppConstants.MOVIES_FILE);
    }

    /**
     * Creates a movie repository using the specified data file.
     *
     * @param filePath the path to the movie data file
     */
    public MovieRepository(String filePath) {
        this.filePath = filePath;
        loadFromFile();
        createSampleDataIfEmpty();
    }

    private void loadFromFile() {
        try {
            List<String> lines = FileStorage.getInstance().readLines(filePath);
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
                    logger.logWarning("⚠️ Invalid movie record format.");
                }
            }
            logger.logInfo("✅ Loaded " + data.size() + " movie(s) from the file.");
        } catch (IOException e) {
            logger.logWarning("📂 Data file not found. Initializing an empty repository.");
            data = new ArrayList<>();
        } catch (NumberFormatException e) {
            logger.logError("⚠️ Failed to parse movie data: " + e.getMessage());
            data = new ArrayList<>();
        }
    }

    /**
     * Finds a movie by its unique identifier.
     *
     * @param id the movie ID
     * @return the matching movie, or {@code null} if no movie with the specified ID exists
     */
    @Override
    public Movie findById(int id) {
        return data.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves a movie to the repository.
     * <p>
     * If a movie with the same ID already exists, it is replaced with the
     * new one. All changes are immediately written to the data file.
     *
     * @param movie the movie to save
     */
    @Override
    public void save(Movie movie) {
        Movie existing = findById(movie.getId());
        if (existing != null) {
            data.remove(existing);
        }
        data.add(movie);
        saveToFile();
    }

    /**
     * Deletes the movie with the specified ID.
     * <p>
     * If a movie is removed successfully, the updated repository is
     * immediately saved to the data file.
     *
     * @param id the ID of the movie to delete
     */
    @Override
    public void delete(int id) {
        boolean removed = data.removeIf(m -> m.getId() == id);
        if (removed) {
            saveToFile();
        }
    }

    /**
     * Writes all movies currently stored in memory to the data file.
     * <p>
     * Existing file content is overwritten.
     */
    public void saveToFile() {
        try {
            List<String> lines = new ArrayList<>();
            for (Movie movie : data) {
                String line = movie.getId() + "|" + movie.getTitle() + "|" + movie.getDuration();
                lines.add(line);
            }
            FileStorage.getInstance().writeLines(filePath, lines);
            logger.logInfo("📂 Saved " + data.size() + " movie(s) to the file.");
        } catch (IOException e) {
            logger.logError("❌ Failed to write movie data to the file: " + e.getMessage());
        }
    }

    /**
     * Creates a predefined set of sample movies if the repository
     * does not contain any data.
     */
    public void createSampleDataIfEmpty() {
        if (data.isEmpty()) {
            data.add(new Movie(1, "Avengers: Endgame", 180));
            data.add(new Movie(2, "Titanic", 195));
            data.add(new Movie(3, "Inception", 148));
            saveToFile();
            logger.logInfo("✅ Sample movie data has been created.");
        }
    }

    /**
     * Finds the first movie whose title contains the specified text.
     * <p>
     * The search is case-insensitive and ignores leading and trailing spaces.
     *
     * @param title the movie title or part of the title to search for
     * @return the first matching movie, or {@code null} if no matching movie is found
     */
    public Movie findByName(String title) {
        if (title == null || title.trim().isEmpty()) {
            return null;
        }

        String lowerTitle = title.toLowerCase().trim();
        return data.stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(lowerTitle))
                .findFirst()
                .orElse(null);
    }
}