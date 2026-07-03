package com.cinema.repository;

import com.cinema.model.Booking;
import com.cinema.util.AppConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingRepository extends BaseRepository<Booking> {

    private String filePath;
    private final FileStorage fileStorage;

    public BookingRepository() {
        this(AppConstants.BOOKINGS_FILE);
    }

    public BookingRepository(String filePath) {
        this.filePath = filePath;
        this.fileStorage = new FileStorage();
        loadFromFile();
    }

    public void loadFromFile() {
        try {
            List<String> lines = fileStorage.readLines(filePath);
            data = new ArrayList<>();

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length == 4) {
//                    int id = Integer.parseInt(parts[0].trim());
                    int showtimeId = Integer.parseInt(parts[0].trim());
                    int seatId = Integer.parseInt(parts[1].trim());
                    String customerName = parts[2].trim();
                    String bookingTime = parts[3].trim();
                    Booking booking = new Booking(showtimeId, seatId, customerName, bookingTime);
                    data.add(booking);
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
              String line = booking.getShowtimeId() + "|"
                      + booking.getSeatId() + "|"
                      + booking.getCustomerName() + "|"
                      + booking.getBookingTime();
              lines.add(line);
          }
          fileStorage.writeLines(filePath, lines);
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

    public void deleteByShowtimeAndSeat(int showtimeId, int seatId) {
        boolean removed = data.removeIf(b -> b.getShowtimeId() == showtimeId && b.getSeatId() == seatId);
        if (removed) {
            saveToFile();
        }
    }

    public boolean isSeatBooked(int showtimeId, int seatId) {
        return data.stream()
                .anyMatch(b -> b.getShowtimeId() == showtimeId && b.getSeatId() == seatId);
    }

    public List<Booking> findByShowtimeId(int showtimeId) {
        return data.stream()
                .filter(b -> b.getShowtimeId() == showtimeId)
                .collect(Collectors.toList());
    }

    public List<Integer> getBookedSeatsByShowtime(int showtimeId) {
        return data.stream()
                .filter(booking -> booking.getShowtimeId() == showtimeId)
                .map(Booking::getSeatId)
                .collect(Collectors.toList());
    }

    public void saveAll(List<Booking> bookings) {
        data.addAll(bookings);
        saveToFile();
    }
}
