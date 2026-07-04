package com.cinema.ui;

import com.cinema.model.Showtime;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.ShowtimeRepository;
import com.cinema.repository.TheaterRepository;
import com.cinema.util.ScannerUtils;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based user interface for managing movie showtimes.
 * <p>
 * This class provides functions for adding, deleting, and viewing
 * showtimes. It interacts directly with the corresponding repositories
 * to retrieve and update showtime information.
 */
public class ShowtimeUI {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final Scanner scanner;

    /**
     * Creates a new {@code ShowtimeUI}.
     *
     * @param showtimeRepository the showtime repository
     * @param movieRepository the movie repository
     * @param theaterRepository the theater repository
     * @param scanner the scanner used to read user input
     */
    public ShowtimeUI(ShowtimeRepository showtimeRepository,
                      MovieRepository movieRepository,
                      TheaterRepository theaterRepository,
                      Scanner scanner) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.scanner = scanner;
    }

    /**
     * Displays the showtime management menu and processes
     * user commands until the user chooses to return.
     */
    public void showMenu() {
        while (true) {
            System.out.println("\n--- SHOWTIME MANAGER ---");
            System.out.println("1. Add new showtime");
            System.out.println("2. Delete showtime");
            System.out.println("3. View showtimes by movie");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addShowtime();
                case 2 -> deleteShowtime();
                case 3 -> listByMovie();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addShowtime() {
        System.out.println("\nMovie List:");
        movieRepository.findAll().forEach(m ->
                System.out.println(m.getId() + ". " + m.getTitle()));

        System.out.print("Enter movie ID: ");
        int movieId = ScannerUtils.readInt(scanner);
        scanner.nextLine();

        if (movieRepository.findById(movieId) == null) {
            System.out.println("Movie does not exist.");
            return;
        }

        System.out.println("\nTheater List:");
        theaterRepository.findAll().forEach(t ->
                System.out.println(t.getId() + ". " + t.getName()));

        System.out.print("Enter theater ID: ");
        int theaterId = ScannerUtils.readInt(scanner);
        scanner.nextLine();

        if (theaterRepository.findById(theaterId) == null) {
            System.out.println("Theater does not exist.");
            return;
        }

        System.out.print("Enter start time (yyyy-MM-dd HH:mm): ");
        String startTime = scanner.nextLine();

        int maxId = showtimeRepository.findAll()
                .stream()
                .mapToInt(Showtime::getId)
                .max()
                .orElse(0);

        int newId = maxId + 1;

        Showtime showtime = new Showtime(newId, movieId, theaterId, startTime);

        showtimeRepository.save(showtime);

        System.out.println("Showtime added successfully. ID: " + newId);
    }

    private void deleteShowtime() {
        listAllShowtimes();

        System.out.print("Enter the showtime ID to delete: ");

        int id = ScannerUtils.readInt(scanner);
        scanner.nextLine();

        if (showtimeRepository.findById(id) == null) {
            System.out.println("Showtime does not exist.");
            return;
        }

        showtimeRepository.delete(id);

        System.out.println("Showtime deleted successfully.");
    }

    private void listAllShowtimes() {
        List<Showtime> list = showtimeRepository.findAll();

        if (list.isEmpty()) {
            System.out.println("No showtimes found.");
            return;
        }

        System.out.println("Showtime List:");

        for (Showtime s : list) {
            System.out.printf(
                    "ID: %d | Movie: %d | Theater: %d | Start Time: %s%n",
                    s.getId(),
                    s.getMovieId(),
                    s.getTheaterId(),
                    s.getStartTime()
            );
        }
    }

    private void listByMovie() {
        System.out.print("Enter movie ID: ");

        int movieId = ScannerUtils.readInt(scanner);
        scanner.nextLine();

        List<Showtime> showtimes = showtimeRepository.findByMovieId(movieId);

        if (showtimes.isEmpty()) {
            System.out.println("No showtimes found.");
            return;
        }

        System.out.println("Showtime List:");

        for (Showtime s : showtimes) {
            System.out.printf(
                    "ID: %d | Theater: %d | Start Time: %s%n",
                    s.getId(),
                    s.getTheaterId(),
                    s.getStartTime()
            );
        }
    }
}