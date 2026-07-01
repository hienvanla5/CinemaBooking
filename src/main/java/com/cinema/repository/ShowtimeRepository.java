package com.cinema.repository;

import com.cinema.model.Showtime;
import com.cinema.util.AppConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShowtimeRepository extends BaseRepository<Showtime> {

    private final FileStorage fileStorage;
    private final String filePath;

    public ShowtimeRepository() {
        this(AppConstants.SHOWTIMES_FILE);
    }

    public ShowtimeRepository(String filePath) {
        this.filePath = filePath;
        this.fileStorage = new FileStorage();
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            List<String> lines = fileStorage.readLines(filePath);
            data = new ArrayList<>();

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    int movieId = Integer.parseInt(parts[1].trim());
                    int theaterId = Integer.parseInt(parts[2].trim());
                    String startTime = parts[3].trim();

                    Showtime showtime = new Showtime(id, movieId, theaterId, startTime);
                    data.add(showtime);
                }
            }
            System.out.println("✅ Loaded " + data.size() + " showtimes.");
        } catch (IOException e) {
            System.out.println("📂 File showtimes.csv not exist, init an empty list.");
            data = new ArrayList<>();
        }
    }

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
            fileStorage.writeLines(filePath, lines);
            System.out.println("📂 Saved " + data.size() + " showtimes.");
        } catch (IOException e) {
            System.err.println("❌ Error writing showtimes.csv file: " + e.getMessage());
        }
    }

    @Override
    public Showtime findById(int id) {
        return data.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Showtime showtime) {
        Showtime existing = findById(showtime.getId());
        if (existing != null) {
            data.remove(existing);
        }
        data.add(showtime);
        saveToFile();
    }

    @Override
    public void delete(int id) {
        boolean removed = data.removeIf(s -> s.getId() == id);
        if (removed) {
            saveToFile();
        }
    }

    public List<Showtime> findByMovieId(int movieId) {
        return data.stream()
                .filter(s -> s.getMovieId() == movieId)
                .collect(Collectors.toList());
    }

    public List<Showtime> findByTheaterId(int theaterId) {
        return data.stream()
                .filter(s -> s.getTheaterId() == theaterId)
                .collect(Collectors.toList());
    }
}
