package com.cinema.ui;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.model.Movie;
import com.cinema.repository.*;
import com.cinema.service.BookingService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final MovieRepository movieRepository = new MovieRepository();
    private static final TheaterRepository theaterRepository = new TheaterRepository();
    private static final BookingRepository bookingRepository = new BookingRepository();
    private static final ShowtimeRepository showtimeRepository = new ShowtimeRepository();
    private static final SeatRepository seatRepository = new SeatRepository();
    private static final BookingService bookingService = new BookingService(bookingRepository, movieRepository, showtimeRepository, seatRepository);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        TheaterUI theaterUI = new TheaterUI(theaterRepository, seatRepository, scanner);
        ShowtimeUI showtimeUI = new ShowtimeUI(showtimeRepository, movieRepository, theaterRepository, scanner);
        BookingUI bookingUI = new BookingUI(bookingService, movieRepository, showtimeRepository, seatRepository, scanner);

        while (true) {
            System.out.println("\n===== Welcome to Cinema Booking Application =====");
            System.out.println("1. View list of movies");
            System.out.println("2. Book a ticket");
            System.out.println("3. Manage theaters");
            System.out.println("4. Manage showtimes");
            System.out.println("5. View empty seats (diagram)");
            System.out.println("6. Exit");
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
                case 1 -> showMovies();
                case 2 -> bookingUI.bookTicket();
                case 3 -> theaterUI.showMenu();
                case 4 -> showtimeUI.showMenu();
                case 5 -> bookingUI.showAvailableSeats();
                case 6 -> {
                    System.out.println("Thank you for using Cinema Booking Application.");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("⚠️ Your choice is invalid.");
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
}
