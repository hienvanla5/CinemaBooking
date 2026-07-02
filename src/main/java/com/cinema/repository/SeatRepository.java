package com.cinema.repository;

import com.cinema.model.Seat;
import com.cinema.util.AppConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SeatRepository extends BaseRepository<Seat> {

    private final FileStorage fileStorage;
    private final String filePath;

    public SeatRepository() {
        this(AppConstants.SEATS_FILE);
    }

    public SeatRepository(String seatFile) {
        this.filePath = seatFile;
        fileStorage = new FileStorage();
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            List<String> lines = fileStorage.readLines(filePath);

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");

                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    int theaterId = Integer.parseInt(parts[1].trim());
                    int row = Integer.parseInt(parts[2].trim());
                    int column = Integer.parseInt(parts[3].trim());
                    Seat seat = new Seat(id, theaterId, row, column);
                    data.add(seat);
                }
            }
            System.out.println("✅ Loaded " + data.size() + " seats.");
        } catch (IOException e) {
            System.out.println("📂 File seats.csv not exist, init empty list.");
            data = new ArrayList<>();
        } catch (NumberFormatException e) {
            System.err.println("⚠️ Error parse number in seats.csv: " + e.getMessage());
            data = new ArrayList<>();
        }
    }

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
            fileStorage.writeLines(filePath, lines);
            System.out.println("📂 Saved " + data.size() + " seats.");
        } catch (IOException e) {
            System.err.println("❌ Error writing seats.csv: " + e.getMessage());
        }
    }

    @Override
    public Seat findById(int id) {
        return data.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Seat seat) {
        Seat existing = findById(seat.getId());
        if (existing != null) {
            data.remove(existing);
        }
        data.add(seat);
        saveToFile();
    }

    @Override
    public void delete(int id) {
        boolean removed = data.removeIf(s -> s.getId() == id);
        if (removed) {
            saveToFile();
        }
    }

    public List<Seat> findByTheaterId(int theaterId) {
        return data.stream()
                .filter(s -> s.getTheaterId() == theaterId)
                .collect(Collectors.toList());
    }

    public void deleteByTheaterId(int theaterId) {
        boolean removed = data.removeIf(s -> s.getTheaterId() == theaterId);
        if (removed) {
            saveToFile();
        }
    }
}
