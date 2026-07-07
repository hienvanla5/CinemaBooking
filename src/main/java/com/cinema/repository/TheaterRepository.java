package com.cinema.repository;

import com.cinema.model.Seat;
import com.cinema.model.Theater;
import com.cinema.util.AppConstants;
import com.cinema.util.AppLogger;
import com.cinema.util.FileStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing {@link Theater} objects.
 * <p>
 * This repository loads theater data from a CSV file, stores it in memory,
 * and persists any modifications back to the file. When a new theater is
 * added, the corresponding seats are automatically initialized.
 */
public class TheaterRepository extends BaseRepository<Theater> {

    private final String filePath;
    private final SeatRepository seatRepository;
    private static final AppLogger logger = AppLogger.getInstance();

    /**
     * Creates a theater repository using the default theater data file.
     */
    public TheaterRepository() {
        this(AppConstants.THEATERS_FILE);
    }

    /**
     * Creates a theater repository using the specified data file.
     *
     * @param filePath the path to the theater data file
     */
    public TheaterRepository(String filePath) {
        this.filePath = filePath;
        this.seatRepository = new SeatRepository(
                filePath.replace("theaters.csv", "seats.csv")
        );
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
                    String name = parts[1].trim();
                    int totalRows = Integer.parseInt(parts[2].trim());
                    int totalColumns = Integer.parseInt(parts[3].trim());

                    data.add(new Theater(id, name, totalRows, totalColumns));
                } else {
                    logger.logWarning("⚠️ Invalid theater record format.");
                }
            }

            logger.logInfo("✅ Loaded " + data.size() + " theater(s) from the file.");

        } catch (IOException e) {
            logger.logWarning("📂 Data file not found. Initializing an empty repository.");
            data = new ArrayList<>();
        }
    }

    /**
     * Writes all theaters currently stored in memory to the data file.
     * <p>
     * Existing file content is overwritten.
     */
    public void saveToFile() {
        try {
            List<String> lines = new ArrayList<>();

            for (Theater theater : data) {
                String line = theater.getId() + "|" +
                        theater.getName() + "|" +
                        theater.getTotalRows() + "|" +
                        theater.getTotalColumns();
                lines.add(line);
            }

            FileStorage.getInstance().writeLines(filePath, lines);
            logger.logInfo("📂 Saved " + data.size() + " theater(s) to the file.");

        } catch (IOException e) {
            logger.logError("❌ Failed to write theater data to the file: " + e.getMessage());
        }
    }

    /**
     * Finds a theater by its unique identifier.
     *
     * @param id the theater ID
     * @return the matching theater, or {@code null} if no theater with the specified ID exists
     */
    @Override
    public Theater findById(int id) {
        return data.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves a theater to the repository.
     * <p>
     * If the theater does not already exist, it is added and its seats
     * are initialized automatically. If it already exists, the existing
     * theater is replaced. All changes are written immediately to the data file.
     *
     * @param theater the theater to save
     */
    @Override
    public void save(Theater theater) {
        Theater existing = findById(theater.getId());

        if (existing == null) {
            data.add(theater);
            saveToFile();
            initializeSeats(theater);
        } else {
            data.remove(existing);
            data.add(theater);
            saveToFile();
        }

        saveToFile();
    }

    /**
     * Deletes the theater with the specified ID.
     * <p>
     * If a theater is removed successfully, the updated repository
     * is immediately saved to the data file.
     *
     * @param id the ID of the theater to delete
     */
    @Override
    public void delete(int id) {
        boolean removed = data.removeIf(t -> t.getId() == id);

        if (removed) {
            saveToFile();
        }
    }

    /**
     * Initializes all seats for a newly created theater.
     * <p>
     * Seat IDs are assigned sequentially, starting from the next available ID
     * after the highest existing seat ID.
     *
     * @param theater the theater whose seats will be created
     */
    private void initializeSeats(Theater theater) {
        int idCounter = 1;

        for (Seat seat : seatRepository.findAll()) {
            if (seat.getId() >= idCounter) {
                idCounter = seat.getId() + 1;
            }
        }

        for (int row = 1; row <= theater.getTotalRows(); row++) {
            for (int col = 1; col <= theater.getTotalColumns(); col++) {
                Seat seat = new Seat(idCounter++, theater.getId(), row, col);
                seatRepository.save(seat);
            }
        }
    }
}