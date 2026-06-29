package com.cinema.repository;

import com.cinema.model.Booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingRepository extends BaseRepository<Booking> {

    private static final String FILE_PATH = "src/main/resources/data/bookings.csv";
    private final FileStorage fileStorage;

    public BookingRepository() {
        this.fileStorage = new FileStorage();
        loadFromFile();
    }

    public void loadFromFile() {
        try {
            List<String> lines = fileStorage.readLines(FILE_PATH);
            data = new ArrayList<>();

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    int movieId = Integer.parseInt(parts[0].trim());
                    int seatId = Integer.parseInt(parts[1].trim());
                    String customerName = parts[2].trim();
                    data.add(new Booking(movieId, seatId, customerName));
                } else {
                    System.err.println("⚠️ Booking line is not formatted correctly (ignore): " + line);
                }
            }
            System.out.println("✅ Loaded " + data.size() + " bookings in file.");
        } catch (IOException e) {
            System.out.println("📂 File bookings.csv is not exist, init an empty list.");
            data = new ArrayList<>();
        } catch (NumberFormatException e) {
            System.err.println("⚠️ Error parse number in booking: " + e.getMessage());
            data = new ArrayList<>();
        }
    }

    public void saveToFile() {
        try {
          List<String> lines = new ArrayList<>();
          for (Booking booking : data) {
              String line = booking.getMovieId() + "|" + booking.getSeatId() + "|" + booking.getCustomerName();
              lines.add(line);
          }
          fileStorage.writeLines(FILE_PATH, lines);
          System.out.println("📂 Saved " + data.size() + " bookings in file.");
        } catch (IOException e) {
            System.err.println("❌ Error when write file bookings.csv: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void save(Booking booking) {
        data.add(booking);
        saveToFile();
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Booking is unsupported by id, let removeByMovieAndSeat if need");
    }

    public void deleteByMovieAndSeat(int movieId, int seatId) {
        boolean removed = data.removeIf(b -> b.getMovieId() == movieId && b.getSeatId() == seatId);
        if (removed) {
            saveToFile();
        }
    }

    public boolean isSeatBooked(int movieId, int seatId) {
        return data.stream()
                .anyMatch(b -> b.getMovieId() == movieId && b.getSeatId() == seatId);
    }

    public List<Booking> findByMovieId(int movieId) {
        return data.stream()
                .filter(b -> b.getMovieId() == movieId)
                .collect(Collectors.toList());
    }
}
