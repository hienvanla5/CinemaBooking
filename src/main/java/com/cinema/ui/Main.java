package com.cinema.ui;

import com.cinema.context.AppContext;
import com.cinema.model.*;
import com.cinema.repository.*;
import com.cinema.service.BookingService;
import com.cinema.simulation.BookingSimulation;
import com.cinema.util.AppLogger;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static final MovieRepository movieRepository = new MovieRepository();
    private static final TheaterRepository theaterRepository = new TheaterRepository();
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
            System.out.println("5. Show seating chart");
            System.out.println("6. Customer booking history");
            System.out.println("7. Revenue statistics");
            System.out.println("8. Simulate concurrent ticket booking");
            System.out.println("9. Measure performance");
            System.out.println("10. Exit");
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
                case 5 -> displaySeatMap();
                case 6 -> viewBookingHistory();
                case 7 -> viewRevenue();
                case 8 -> simulateBooking();
                case 9 -> measurePerformance();
                case 10 -> {
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

    private static void displaySeatMap() {
        try {
            int showtimeId = readInt("Enter showtime ID: ");

            Showtime showtime = AppContext.getInstance().getShowtimeRepository().findById(showtimeId);
            if (showtime == null) {
                System.out.println("❌ Showtime not found.");
                return;
            }

            Theater theater = AppContext.getInstance().getTheaterRepository().findById(showtime.getTheaterId());
            if (theater == null) {
                System.out.println("❌ Theater not found.");
                return;
            }

            List<Integer> bookedSeats = AppContext.getInstance().getBookingRepository().getBookedSeatsByShowtime(showtimeId);

            List<Seat> allSeats = AppContext.getInstance().getSeatRepository().findByTheaterId(theater.getId());

            Set<Integer> bookedSeatIds = new HashSet<>(bookedSeats);

            System.out.println("\n📽️Seat diagram - Showtime ID: " + showtime);
            System.out.println("Theater: " + theater.getName() + " (" + theater.getTotalRows() + "x" + theater.getTotalColumns() + ")");
            System.out.println("✅ [X] = Booked, ▢ [] = Available");
            System.out.println("-----------------------------");

            int totalRows = theater.getTotalRows();
            int totalCols = theater.getTotalColumns();

            System.out.println("      ");
            for (int col = 0; col < totalCols; col++) {
                System.out.printf("%3d", col + 1);
            }
            System.out.println();

            for  (int row = 0; row < totalRows; row++) {
                System.out.printf("%2d", row + 1);

                for (int col = 0; col < totalCols; col++) {
                    int seatId = row * totalCols + col + 1;
                    if (bookedSeatIds.contains(seatId)) {
                        System.out.print(" [X]");
                    } else {
                        System.out.print(" [ ]");
                    }
                }
                System.out.println();
            }

            System.out.println("---------------------------------");

        } catch (NumberFormatException e) {
            System.out.println("⚠️ Please enter valid number!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            AppLogger.getInstance().logError("Error displaying seat map", e);
        }
    }

    private static void viewBookingHistory() {
        try {
            System.out.print("Enter customer name (or a part of name): ");
            String name = scanner.nextLine();
            if (name.isEmpty()) {
                System.out.println("⚠️Name cannot be blank!");
                return;
            }

            List<Booking> bookings = AppContext.getInstance().getBookingRepository()
                    .findByCustomerName(name);

            if (bookings.isEmpty()) {
                System.out.println("📭No bookings found for the customer: " + name);
                return;
            }

            System.out.println("\n CUSTOMER BOOKING HISTORY: " + name);
            System.out.println("-------------------------------------------");
            System.out.printf("%-12s | %-8s | %-15s | %-10s%n", "Showtime ID", "Seat ID", "Customer", "Price");
            System.out.println("-------------------------------------------");

            for (Booking b : bookings) {
                System.out.printf("%-12d | %-8s | %-15s | %-10s%n", b.getShowtimeId(), b.getSeatId(), b.getCustomerName(), b.getPrice());
            }
            System.out.println("-------------------------------------------");
            System.out.println("✅ Total bookings: " + bookings.size());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            AppLogger.getInstance().logError("Error viewing booking history", e);
        }
    }

    private static void viewRevenue() {
        try {
            BookingRepository bookingRepo = AppContext.getInstance().getBookingRepository();
            List<Booking> allBookings = bookingRepo.findAll();

            if (allBookings.isEmpty()) {
                System.out.println("📭 No tickets have been sold yet!");
                return;
            }

            double totalRevenue = allBookings.stream()
                    .mapToDouble(Booking::getPrice)
                    .sum();

            int totalTickets = allBookings.size();

            System.out.println("\n💰 REVENUE STATISTICS");
            System.out.println("-----------------------------------------");
            System.out.printf("📊 Total tickets have been sold: %d tickets%n", totalTickets);
            System.out.printf("💵 Total revenue: %,.0f VND%n", totalRevenue);
            System.out.printf("📈 Average revenue per ticket: %,.0f VND%n", totalRevenue /totalTickets);
            System.out.println("-------------------------------------------");

            System.out.println("\n🎬 Revenue by showtime:");
            Map<Integer, Double> revenueByShowtime = allBookings.stream()
                    .collect(Collectors.groupingBy(
                            Booking::getShowtimeId,
                            Collectors.summingDouble(Booking::getPrice)
                    ));

            for (Map.Entry<Integer, Double> entry : revenueByShowtime.entrySet()) {
                int showtimeId = entry.getKey();
                double revenue = entry.getValue();

                Showtime st = AppContext.getInstance().getShowtimeRepository().findById(showtimeId);
                String movieName = "Unknown";
                if (st != null) {
                    Movie movie = AppContext.getInstance().getMovieRepository().findById(st.getMovieId());
                    if (movie != null) {
                        movieName = movie.getTitle();
                    }
                }
                long count = allBookings.stream()
                        .filter(b -> b.getShowtimeId() == showtimeId)
                        .count();
                System.out.printf("   - Showtime %d (%s): %d tickets, %,.0f VND%n", showtimeId, movieName, count, revenue);
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            AppLogger.getInstance().logError("Error viewing revenue", e);
        }
    }

    public static void measurePerformance() {
        try {
            int numberOfUsers = 50;
            int showtimeId = 2;
            int seatId = 59;

            System.out.println("🚀 Start measure performance with " + numberOfUsers + " users...");
            long startTime = System.currentTimeMillis();

            BookingSimulation simulation = new BookingSimulation(
                    AppContext.getInstance().getBookingService()
            );
            BookingSimulation.SimulationResult result = simulation.simulateConcurrentBooking(showtimeId, seatId, numberOfUsers);

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            System.out.println("✅ Result:");
            System.out.println("    - Success: " + result.getSuccess());
            System.out.println("    - Failure: " + result.getFailure());
            System.out.println("    - Total time: " + duration + " ms");
            System.out.println("    - Average duration/user: " + (duration / numberOfUsers) + " ms");
        } catch (Exception e) {
            System.err.println("❌ Error measure performance: " + e.getMessage());
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.next().trim();
                if (input.isEmpty()) {
                    System.out.println("⚠️ Please enter number.");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter valid number.");
            }
        }
    }
}
