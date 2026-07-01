package com.cinema.repository;

import com.cinema.model.Theater;
import com.cinema.util.AppConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TheaterRepository extends BaseRepository<Theater> {

    private final FileStorage fileStorage;
    private final String filePath;

    public TheaterRepository() {
        this(AppConstants.THEATERS_FILE);
    }

    public TheaterRepository(String filePath) {
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
                    String name = parts[1].trim();
                    int totalRows = Integer.parseInt(parts[2].trim());
                    int totalColumns = Integer.parseInt(parts[3].trim());
                    data.add(new Theater(id, name , totalRows, totalColumns));
                }
            }
            System.out.println("✅ Loaded " + data.size() + " theaters.");
        } catch (IOException e) {
            System.out.println("📂 File theaters.csv not exist. Init an empty list.");
            data = new ArrayList<>();
        }
    }

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
            fileStorage.writeLines(filePath, lines);
            System.out.println("📂 Saved " + data.size() + " theaters.");
        } catch (IOException e) {
            System.err.println("❌ Error writing theaters.csv file." + e.getMessage());
        }
    }

    @Override
    public Theater findById(int id) {
        return data.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Theater theater) {
        Theater existing = findById(theater.getId());
        if (existing != null) {
            data.remove(existing);
        }
        data.add(theater);
        saveToFile();
    }

    @Override
    public void delete(int id) {
        boolean removed = data.removeIf(t -> t.getId() == id);
        if (removed) {
            saveToFile();
        }
    }
}
