package com.cinema.ui;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.model.Movie;
import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowtimeRepository;
import com.cinema.service.BookingService;
import com.cinema.util.ScannerUtils;

import java.util.List;
import java.util.Scanner;

public class BookingUI {

    private final BookingService bookingService;
    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final Scanner scanner;

    public BookingUI(BookingService bookingService, MovieRepository movieRepository, ShowtimeRepository showtimeRepository, SeatRepository seatRepository, Scanner scanner) {
        this.bookingService = bookingService;
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
        this.scanner = scanner;
    }

    public void bookTicket() {
//        showMovies();

        int showtimeId = inputShowtimeId();
        if (showtimeId == -1) {
            return;
        }

        int seatId = inputSeatId();
        if (seatId == -1) {
            return;
        }

        String customerName = inputCustomerName();

        try {
            Booking booking = bookingService.bookSeat(showtimeId, seatId, customerName);
        } catch (InvalidInputException e) {
            showError("Error data: " + e.getMessage());
        } catch (SeatUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }

    private int inputShowtimeId() {
        System.out.print("Enter showtime ID: ");
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Movie ID must be a number. Please re-enter.");
            return -1;
        }
    }

    private int inputSeatId() {
        System.out.print("Enter seat number (1-10): ");
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Seat ID must be a number. Please re-enter.");
            return -1;
        }
    }

    private String inputCustomerName() {
        System.out.print("Enter customer name: ");
        return scanner.nextLine();
    }

    private void showError(String message) {
        System.out.println("❌ " + message);
    }

    public void showAvailableSeats() {
        System.out.print("Enter showtime ID: ");
        int showtimeId = ScannerUtils.readInt(scanner);
        scanner.nextLine();
        Showtime showtime = showtimeRepository.findById(showtimeId);
        if (showtime == null) {
            System.out.println("Showtime not exist.");
            return;
        }

        List<Seat> available = bookingService.getAvailableSeats(showtimeId);
        if (available.isEmpty()) {
            System.out.println("No available seats.");
            return;
        }
        System.out.println("Available seats for showtime " + showtimeId + ":");
        for (Seat seat : available) {
            System.out.printf("Seat %d (Row %d, Column%d)%n", seat.getId(), seat.getRow(), seat.getColumn());
        }
    }
}
