package com.cinema.ui;

import com.cinema.model.Showtime;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.ShowtimeRepository;
import com.cinema.repository.TheaterRepository;
import com.cinema.util.ScannerUtils;

import java.util.List;
import java.util.Scanner;

public class ShowtimeUI {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final Scanner scanner;

    public ShowtimeUI(ShowtimeRepository showtimeRepository, MovieRepository movieRepository, TheaterRepository theaterRepository, Scanner scanner) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.scanner = scanner;
    }

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
                case 4 -> { return; }
                default -> System.out.println("Your choice is invalid.");
            }
        }
    }

    private void addShowtime() {
        System.out.println("\nMovie list:");
        movieRepository.findAll().forEach(m ->
                System.out.println(m.getId() + ". " + m.getTitle()));
        System.out.print("Enter movie ID: ");
        int movieId = ScannerUtils.readInt(scanner);
        scanner.nextLine();
        if (movieRepository.findById(movieId) == null) {
            System.out.println("Movie not exist.");
            return;
        }

        System.out.println("\nTheater list:");
        theaterRepository.findAll().forEach(t ->
                System.out.println(t.getId() + ". " + t.getName()));
        System.out.print("Enter theater ID: ");
        int theaterId = ScannerUtils.readInt(scanner);
        scanner.nextLine();
        if (theaterRepository.findById(theaterId) == null) {
            System.out.println("Theater not exist.");
            return;
        }

        System.out.print("Enter start time (yyyy-MM-dd HH:mm): ");
        String startTime = scanner.nextLine();

        int maxId = showtimeRepository.findAll().stream().mapToInt(Showtime::getId).max().orElse(0);

        int newId = maxId + 1;
        Showtime showtime = new Showtime(newId, movieId, theaterId, startTime);
        showtimeRepository.save(showtime);
        System.out.println("Added showtime successfully! ID: " + newId);
    }

    private void deleteShowtime() {
        listAllShowtimes();
        System.out.print("Enter showtime ID to delete: ");
        int id = ScannerUtils.readInt(scanner);
        scanner.nextLine();
        if (showtimeRepository.findById(id) == null) {
            System.out.println("Showtime not exist.");
            return;
        }
        showtimeRepository.delete(id);
        System.out.println("Deleted showtime successfully.");
    }

    private void listAllShowtimes() {
        List<Showtime> list = showtimeRepository.findAll();
        if (list.isEmpty()) {
            System.out.println("No showtimes found.");
            return;
        }
        System.out.println("Showtime list:");
        for (Showtime s : list) {
            System.out.printf("ID: %d | Movie: %d | Theater: %d | Start time: %s%n", s.getId(), s.getMovieId(), s.getTheaterId(), s.getStartTime());
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
        System.out.println("Showtime list:");
        for (Showtime s : showtimes) {
            System.out.printf("ID: %d | Theater: %d | Start time: %s%n", s.getId(), s.getTheaterId(), s.getStartTime());
        }
    }
}
