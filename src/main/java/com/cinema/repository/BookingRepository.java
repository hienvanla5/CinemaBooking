package com.cinema.repository;

import com.cinema.model.Booking;
import com.cinema.util.AppConstants;
import com.cinema.util.FileStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository implementation for managing {@link Booking} objects.
 * <p>
 * This repository stores booking information in memory and persists
 * the data to a CSV file. It provides operations for loading, saving,
 * querying, and deleting bookings.
 */
public class BookingRepository extends BaseRepository<Booking> {

    private String filePath;

    /**
     * Creates a repository using the default bookings data file.
     */
    public BookingRepository() {
        this(AppConstants.BOOKINGS_FILE);
    }

    /**
     * Creates a repository using the specified data file.
     *
     * @param filePath the path to the bookings data file
     */
    public BookingRepository(String filePath) {
        this.filePath = filePath;
        loadFromFile();
    }

    /**
     * Loads booking data from the configured CSV file into memory.
     * <p>
     * Invalid or malformed lines are ignored. If the file does not
     * exist, an empty repository is initialized.
     */
    public void loadFromFile() {
        try {
            List<String> lines = FileStorage.getInstance().readLines(filePath);
            data = new ArrayList<>();

            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    int showtimeId = Integer.parseInt(parts[0].trim());
                    int seatId = Integer.parseInt(parts[1].trim());
                    String customerName = parts[2].trim();
                    String bookingTime = parts[3].trim();

                    data.add(new Booking(showtimeId, seatId, customerName, bookingTime));
                } else {
                    System.err.println("Warning: Invalid booking record skipped: " + line);
                }
            }

            System.out.println("Loaded " + data.size() + " bookings from file.");
        } catch (IOException e) {
            System.out.println("Booking data file not found. Initialized with an empty repository.");
            data = new ArrayList<>();
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse booking data: " + e.getMessage());
            data = new ArrayList<>();
        }
    }

    /**
     * Saves all bookings currently stored in memory to the CSV file.
     */
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

            FileStorage.getInstance().writeLines(filePath, lines);
            System.out.println("Saved " + data.size() + " bookings to file.");
        } catch (IOException e) {
            System.err.println("Failed to save bookings to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Saves a booking to the repository and persists the updated data.
     *
     * @param booking the booking to save
     */
    @Override
    public void save(Booking booking) {
        data.add(booking);
        saveToFile();
    }

    /**
     * Deleting a booking by a single ID is not supported because
     * bookings are uniquely identified by both showtime ID and seat ID.
     *
     * @param id ignored
     * @throws UnsupportedOperationException always thrown
     */
    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException(
                "Deleting bookings by ID is not supported. Use deleteByShowtimeAndSeat() instead."
        );
    }

    /**
     * Deletes a booking by its showtime ID and seat ID.
     *
     * @param showtimeId the showtime ID
     * @param seatId the seat ID
     */
    public void deleteByShowtimeAndSeat(int showtimeId, int seatId) {
        boolean removed = data.removeIf(
                b -> b.getShowtimeId() == showtimeId && b.getSeatId() == seatId
        );

        if (removed) {
            saveToFile();
        }
    }

    /**
     * Checks whether a seat has already been booked for a specific showtime.
     *
     * @param showtimeId the showtime ID
     * @param seatId the seat ID
     * @return {@code true} if the seat has already been booked;
     *         {@code false} otherwise
     */
    public boolean isSeatBooked(int showtimeId, int seatId) {
        return data.stream()
                .anyMatch(b ->
                        b.getShowtimeId() == showtimeId &&
                                b.getSeatId() == seatId
                );
    }

    /**
     * Returns all bookings associated with the specified showtime.
     *
     * @param showtimeId the showtime ID
     * @return a list of bookings for the specified showtime
     */
    public List<Booking> findByShowtimeId(int showtimeId) {
        return data.stream()
                .filter(b -> b.getShowtimeId() == showtimeId)
                .collect(Collectors.toList());
    }

    /**
     * Returns the IDs of all booked seats for the specified showtime.
     *
     * @param showtimeId the showtime ID
     * @return a list of booked seat IDs
     */
    public List<Integer> getBookedSeatsByShowtime(int showtimeId) {
        return data.stream()
                .filter(booking -> booking.getShowtimeId() == showtimeId)
                .map(Booking::getSeatId)
                .collect(Collectors.toList());
    }

    /**
     * Saves multiple bookings to the repository in a single operation
     * and persists the updated data.
     *
     * @param bookings the bookings to save
     */
    public void saveAll(List<Booking> bookings) {
        data.addAll(bookings);
        saveToFile();
    }
}