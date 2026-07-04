package com.cinema.repository;

import com.cinema.model.Seat;
import com.cinema.util.AppConstants;
import com.cinema.util.AppLogger;
import com.cinema.util.FileStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository for managing {@link Seat} objects.
 * <p>
 * This repository loads seat data from a CSV file, stores it in memory,
 * and persists any modifications back to the file.
 */
public class SeatRepository extends BaseRepository<Seat> {

    private final String filePath;
    private static final AppLogger logger = AppLogger.getInstance();

    /**
     * Creates a seat repository using the default seat data file.
     */
    public SeatRepository() {
        this(AppConstants.SEATS_FILE);
    }

    /**
     * Creates a seat repository using the specified data file.
     *
     * @param seatFile the path to the seat data file
     */
    public SeatRepository(String seatFile) {
        this.filePath = seatFile;
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            List<String> lines = FileStorage.getInstance().readLines(filePath);

            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    int theaterId = Integer.parseInt(parts[1].trim());
                    int row = Integer.parseInt(parts[2].trim());
                    int column = Integer.parseInt(parts[3].trim());

                    Seat seat = new Seat(id, theaterId, row, column);
                    data.add(seat);
                } else {
                    logger.warning("⚠️ Invalid seat record format.");
                }
            }

            logger.info("✅ Loaded " + data.size() + " seat(s) from the file.");

        } catch (IOException e) {
            logger.warning("📂 Data file not found. Initializing an empty repository.");
            data = new ArrayList<>();

        } catch (NumberFormatException e) {
            logger.severe("⚠️ Failed to parse seat data: " + e.getMessage());
            data = new ArrayList<>();
        }
    }

    /**
     * Writes all seats currently stored in memory to the data file.
     * <p>
     * Existing file content is overwritten.
     */
    public void saveToFile() {
        try {
            List<String> lines = new ArrayList<>();

            for (Seat seat : data) {
                String line = seat.getId() + "|"
                        + seat.getTheaterId() + "|"
                        + seat.getRow() + "|"
                        + seat.getColumn();
                lines.add(line);
            }

            FileStorage.getInstance().writeLines(filePath, lines);
            logger.info("📂 Saved " + data.size() + " seat(s) to the file.");

        } catch (IOException e) {
            logger.severe("❌ Failed to write seat data to the file: " + e.getMessage());
        }
    }

    /**
     * Finds a seat by its unique identifier.
     *
     * @param id the seat ID
     * @return the matching seat, or {@code null} if no seat with the specified ID exists
     */
    @Override
    public Seat findById(int id) {
        return data.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves a seat to the repository.
     * <p>
     * If a seat with the same ID already exists, it is replaced.
     * All changes are immediately written to the data file.
     *
     * @param seat the seat to save
     */
    @Override
    public void save(Seat seat) {
        Seat existing = findById(seat.getId());

        if (existing != null) {
            data.remove(existing);
        }

        data.add(seat);
        saveToFile();
    }

    /**
     * Deletes the seat with the specified ID.
     * <p>
     * If a seat is removed successfully, the updated repository
     * is immediately saved to the data file.
     *
     * @param id the ID of the seat to delete
     */
    @Override
    public void delete(int id) {
        boolean removed = data.removeIf(s -> s.getId() == id);

        if (removed) {
            saveToFile();
        }
    }

    /**
     * Returns all seats belonging to the specified theater.
     *
     * @param theaterId the theater ID
     * @return a list of seats in the specified theater
     */
    public List<Seat> findByTheaterId(int theaterId) {
        return data.stream()
                .filter(s -> s.getTheaterId() == theaterId)
                .collect(Collectors.toList());
    }

    /**
     * Deletes all seats that belong to the specified theater.
     * <p>
     * If any seats are removed, the updated repository is
     * immediately saved to the data file.
     *
     * @param theaterId the theater ID
     */
    public void deleteByTheaterId(int theaterId) {
        boolean removed = data.removeIf(s -> s.getTheaterId() == theaterId);

        if (removed) {
            saveToFile();
        }
    }
}