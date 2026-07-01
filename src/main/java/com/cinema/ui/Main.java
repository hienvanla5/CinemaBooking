package com.cinema.ui;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.model.Movie;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.MovieRepository;
import com.cinema.service.BookingService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final MovieRepository movieRepository = new MovieRepository();
    private static final BookingRepository bookingRepository = new BookingRepository();
    private static final BookingService bookingService = new BookingService(bookingRepository, movieRepository);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== Welcome to Cinema Booking Application =====");
            System.out.println("1. View list of movies");
            System.out.println("2. Book a ticket");
            System.out.println("3. See list of reserved seats");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a valid choice.");
                continue;
            }

            switch (choice) {
                case 1:
                    showMovies();
                    break;
                case 2:
                    bookTicket();
                    break;
                case 3:
                    showBookedSeats();
                    break;
                case 4:
                    System.out.println("Thank you for using the system!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("⚠️ Your choice is invalid.");
            }
        }
    }

    private static void showMovies() {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            System.out.println("📂 There are no movies yet. Please add movies first.");
            return;
        }
        System.out.println("\n📋 MOVIE LIST:");
        System.out.println("ID | Title | Duration");
        System.out.println("-------------------------");
        for (Movie m : movies) {
            System.out.printf("%-4d|%-30s| %d%n", m.getId(), m.getTitle(), m.getDuration());
        }
    }

    private static void bookTicket() {
        showMovies();

        int movieId = inputMovieId();
        if (movieId == -1) {
            return;
        }

        Movie movie = movieRepository.findById(movieId);
        if (movie == null) {
            showMovieNotFound(movieId);
            return;
        }

        int seatId = inputSeatId();
        if (seatId == -1) {
            return;
        }

        String customerName = inputCustomerName();

        try {
            Booking booking = bookingService.bookSeat(movieId, seatId, customerName);
            showBookingSuccess(movie, seatId, customerName);
        } catch (InvalidInputException e) {
            showError("Error data: " + e.getMessage());
        } catch (SeatUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int inputMovieId() {
        System.out.print("Enter movie ID: ");
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Movie ID must be a number. Please re-enter.");
            return -1;
        }
    }

    private static int inputSeatId() {
        System.out.print("Enter seat number (1-10): ");
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Seat ID must be a number. Please re-enter.");
            return -1;
        }
    }

    private static String inputCustomerName() {
        System.out.print("Enter customer name: ");
        return scanner.nextLine();
    }

    private static void showBookingSuccess(Movie movie, int seatId, String customerName) {
        System.out.println("🎉 Booking successfully!");
        System.out.println(" Ticket number: The movie " + movie.getTitle()
                + ", seat number " + seatId
                + ", customer: " + customerName);
    }

    private static void showMovieNotFound(int movieId) {
        System.out.println("⚠️ Not found movie with ID: " + movieId);
    }

    private static void showError(String message) {
        System.out.println("❌ " + message);
    }

    public static void showBookedSeats() {
        showMovies();

        System.out.print("Enter movie ID to see list of reserved seats: ");
        int movieId;
        try {
            movieId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Movie ID must be number.");
            return;
        }

        Movie movie = movieRepository.findById(movieId);
        if (movie == null) {
            System.out.println("⚠️ Film not found.");
            return;
        }

        List<Integer> bookedSeats = bookingRepository.getBookedSeats(movieId);
        if (bookedSeats.isEmpty()) {
            System.out.println("📌 No seats have been booked for this film yet.");
        } else {
            System.out.println("🪑 Seats booked for \"" + movie.getTitle() + "\" film: " + bookedSeats);
        }
    }
}
