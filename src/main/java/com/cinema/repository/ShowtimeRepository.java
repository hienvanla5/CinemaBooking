package com.cinema.repository;

import com.cinema.model.Showtime;
import com.cinema.util.AppConstants;
import com.cinema.util.AppLogger;
import com.cinema.util.FileStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository for managing {@link Showtime} objects.
 * <p>
 * This repository loads showtime data from a CSV file, stores it in memory,
 * and persists any modifications back to the file.
 */
public class ShowtimeRepository extends BaseRepository<Showtime> {

    private final String filePath;
    private static final AppLogger logger = AppLogger.getInstance();

    /**
     * Creates a showtime repository using the default showtime data file.
     */
    public ShowtimeRepository() {
        this(AppConstants.SHOWTIMES_FILE);
    }

    /**
     * Creates a showtime repository using the specified data file.
     *
     * @param filePath the path to the showtime data file
     */
    public ShowtimeRepository(String filePath) {
        this.filePath = filePath;
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            List<String> lines = FileStorage.getInstance().readLines(filePath);
            data = new ArrayList<>();

            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    int movieId = Integer.parseInt(parts[1].trim());
                    int theaterId = Integer.parseInt(parts[2].trim());
                    String startTime = parts[3].trim();

                    Showtime showtime = new Showtime(id, movieId, theaterId, startTime);
                    data.add(showtime);
                } else {
                    logger.warning("⚠️ Invalid showtime record format.");
                }
            }

            logger.info("✅ Loaded " + data.size() + " showtime(s) from the file.");

        } catch (IOException e) {
            logger.warning("📂 Data file not found. Initializing an empty repository.");
            data = new ArrayList<>();
        }
    }

    /**
     * Writes all showtimes currently stored in memory to the data file.
     * <p>
     * Existing file content is overwritten.
     */
    public void saveToFile() {
        try {
            List<String> lines = new ArrayList<>();

            for (Showtime showtime : data) {
                String line = showtime.getId() + "|"
                        + showtime.getMovieId() + "|"
                        + showtime.getTheaterId() + "|"
                        + showtime.getStartTime();
                lines.add(line);
            }

            FileStorage.getInstance().writeLines(filePath, lines);
            logger.info("📂 Saved " + data.size() + " showtime(s) to the file.");

        } catch (IOException e) {
            logger.severe("❌ Failed to write showtime data to the file: " + e.getMessage());
        }
    }

    /**
     * Finds a showtime by its unique identifier.
     *
     * @param id the showtime ID
     * @return the matching showtime, or {@code null} if no showtime with the specified ID exists
     */
    @Override
    public Showtime findById(int id) {
        return data.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves a showtime to the repository.
     * <p>
     * If a showtime with the same ID already exists, it is replaced.
     * All changes are immediately written to the data file.
     *
     * @param showtime the showtime to save
     */
    @Override
    public void save(Showtime showtime) {
        Showtime existing = findById(showtime.getId());

        if (existing != null) {
            data.remove(existing);
        }

        data.add(showtime);
        saveToFile();
    }

    /**
     * Deletes the showtime with the specified ID.
     * <p>
     * If a showtime is removed successfully, the updated repository
     * is immediately saved to the data file.
     *
     * @param id the ID of the showtime to delete
     */
    @Override
    public void delete(int id) {
        boolean removed = data.removeIf(s -> s.getId() == id);

        if (removed) {
            saveToFile();
        }
    }

    /**
     * Returns all showtimes for the specified movie.
     *
     * @param movieId the movie ID
     * @return a list of showtimes associated with the specified movie
     */
    public List<Showtime> findByMovieId(int movieId) {
        return data.stream()
                .filter(s -> s.getMovieId() == movieId)
                .collect(Collectors.toList());
    }

    /**
     * Returns all showtimes scheduled in the specified theater.
     *
     * @param theaterId the theater ID
     * @return a list of showtimes scheduled in the specified theater
     */
    public List<Showtime> findByTheaterId(int theaterId) {
        return data.stream()
                .filter(s -> s.getTheaterId() == theaterId)
                .collect(Collectors.toList());
    }
}