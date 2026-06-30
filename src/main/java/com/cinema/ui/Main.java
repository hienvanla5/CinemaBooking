package com.cinema.ui;

import com.cinema.model.Movie;
import com.cinema.repository.MovieRepository;
import com.cinema.service.BookingService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final MovieRepository movieRepository = new MovieRepository();
    private static final BookingService bookingService = new BookingService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== Welcome to Cinema Booking Application =====");
            System.out.println("1. View movies");
            System.out.println("2. Book a ticket");
            System.out.println("3. Exit");
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

        System.out.print("Enter movie ID: ");
        String movieIdInput = scanner.nextLine();
        int movieId;
        try {
            movieId = Integer.parseInt(movieIdInput);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Movie ID must be a number.");
            return;
        }

        Movie movie = movieRepository.findById(movieId);
        if (movie == null) {
            System.out.println("⚠️ Not found movie with ID: " + movieId);
            return;
        }

        System.out.print("Enter seat number (1-10): ");
        String seatIdInput = scanner.nextLine();
        int seatId;
        try {
            seatId = Integer.parseInt(seatIdInput);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Seat ID must be a number.");
            return;
        }

        System.out.print("Enter customer name: ");
        String customerNameInput = scanner.nextLine();

        boolean success = bookingService.bookSeat(movieId, seatId, customerNameInput);
        if (success) {
            System.out.println("🎉 Booking Successful for movie: " + movie.getTitle());
        } else {
            System.out.println("❌ Booking Failed. Please check the information again.");
        }
    }
}
