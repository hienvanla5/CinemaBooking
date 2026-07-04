package com.cinema.ui;

import com.cinema.context.AppContext;
import com.cinema.model.Movie;
import com.cinema.model.Showtime;
import com.cinema.repository.*;
import com.cinema.service.BookingService;
import com.cinema.simulation.BookingSimulation;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static final MovieRepository movieRepository = new MovieRepository();
    private static final TheaterRepository theaterRepository = new TheaterRepository();
    private static final BookingRepository bookingRepository = new BookingRepository();
    private static final ShowtimeRepository showtimeRepository = new ShowtimeRepository();
    private static final SeatRepository seatRepository = new SeatRepository();
    private static final BookingService bookingService = AppContext.getInstance().getBookingService();
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
            System.out.println("6. Simulate concurrent ticket booking");
            System.out.println("7. Exit");
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
                case 6 -> simulateBooking();
                case 7 -> {
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

    private static void simulateBooking() {
        showShowtimes();

        System.out.print("Enter showtime ID: ");
        int showtimeId = Integer.parseInt(scanner.nextLine());

        Showtime showtime = showtimeRepository.findById(showtimeId);
        if (showtime == null) {
            System.out.println("📂 Showtime not exist.");
            return;
        }

        System.out.print("Enter seat ID: ");
        int seatId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter number of users (thread): ");
        int numberOfUsers = Integer.parseInt(scanner.nextLine());

        BookingSimulation simulation = new BookingSimulation(bookingService);
        System.out.println("⏳ Running simulation with " + numberOfUsers + " users...");
        long startTime = System.currentTimeMillis();

        BookingSimulation.SimulationResult result = simulation.simulateConcurrentBooking(showtimeId, seatId, numberOfUsers);

        long endTime = System.currentTimeMillis();
        System.out.println("⏱️ Time: " + (endTime - startTime) + " ms");

        System.out.println("\n📊 SIMULATION RESULTS:");
        System.out.println(ANSI_GREEN + "✅ Success: " + result.getSuccess() + ANSI_RESET);
        System.out.println(ANSI_RED + "❌ Failure: " + result.getFailure() + ANSI_RESET);
    }

    private static void showShowtimes() {
        List<Showtime> all = showtimeRepository.findAll();
        if (all.isEmpty()) {
            System.out.println("📂 No such showtime yet.");
            return;
        }
        System.out.println("📋 SHOWTIME LIST:");
        System.out.println("ID | Movie ID | Theater ID | Show Duration");
        for (Showtime s : all) {
            System.out.printf("%-4d | %-8d | %-9d | %s%n", s.getId(), s.getMovieId(), s.getTheaterId(), s.getStartTime());
        }
    }
}
