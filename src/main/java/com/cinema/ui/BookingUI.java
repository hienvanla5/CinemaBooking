package com.cinema.ui;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowtimeRepository;
import com.cinema.service.BookingService;
import com.cinema.util.ScannerUtils;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based user interface for movie ticket booking.
 * <p>
 * This class handles user interaction related to booking tickets
 * and displaying available seats. It delegates business logic to
 * {@link BookingService}.
 */
public class BookingUI {

    private final BookingService bookingService;
    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final Scanner scanner;

    /**
     * Creates a booking user interface.
     *
     * @param bookingService the booking service
     * @param movieRepository the movie repository
     * @param showtimeRepository the showtime repository
     * @param seatRepository the seat repository
     * @param scanner the scanner used for console input
     */
    public BookingUI(BookingService bookingService,
                     MovieRepository movieRepository,
                     ShowtimeRepository showtimeRepository,
                     SeatRepository seatRepository,
                     Scanner scanner) {
        this.bookingService = bookingService;
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
        this.scanner = scanner;
    }

    /**
     * Guides the user through the ticket booking process.
     * <p>
     * The user is prompted to enter the showtime ID, seat ID,
     * and customer name. The booking request is then delegated
     * to the booking service.
     */
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
            showError("Invalid input: " + e.getMessage());

        } catch (SeatUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads the showtime ID from user input.
     *
     * @return the entered showtime ID, or {@code -1} if the input is invalid
     */
    private int inputShowtimeId() {
        System.out.print("Enter showtime ID: ");

        String input = scanner.nextLine();

        try {
            return Integer.parseInt(input);

        } catch (NumberFormatException e) {
            System.out.println("⚠️ Showtime ID must be a number.");
            return -1;
        }
    }

    /**
     * Reads the seat ID from user input.
     *
     * @return the entered seat ID, or {@code -1} if the input is invalid
     */
    private int inputSeatId() {
        System.out.print("Enter seat ID: ");

        String input = scanner.nextLine();

        try {
            return Integer.parseInt(input);

        } catch (NumberFormatException e) {
            System.out.println("⚠️ Seat ID must be a number.");
            return -1;
        }
    }

    /**
     * Reads the customer's name from user input.
     *
     * @return the entered customer name
     */
    private String inputCustomerName() {
        System.out.print("Enter customer name: ");
        return scanner.nextLine();
    }

    /**
     * Displays an error message in the console.
     *
     * @param message the error message to display
     */
    private void showError(String message) {
        System.out.println("❌ " + message);
    }

    /**
     * Displays all available seats for a selected showtime.
     * <p>
     * If the showtime does not exist or no seats are available,
     * an appropriate message is displayed.
     */
    public void showAvailableSeats() {
        System.out.print("Enter showtime ID: ");

        int showtimeId = ScannerUtils.readInt(scanner);
        scanner.nextLine();

        Showtime showtime = showtimeRepository.findById(showtimeId);

        if (showtime == null) {
            System.out.println("Showtime does not exist.");
            return;
        }

        List<Seat> available = bookingService.getAvailableSeats(showtimeId);

        if (available.isEmpty()) {
            System.out.println("No seats are available.");
            return;
        }

        System.out.println("Available seats for showtime " + showtimeId + ":");

        for (Seat seat : available) {
            System.out.printf(
                    "Seat %d (Row %d, Column %d)%n",
                    seat.getId(),
                    seat.getRow(),
                    seat.getColumn()
            );
        }
    }
}