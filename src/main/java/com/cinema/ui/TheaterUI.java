package com.cinema.ui;

import com.cinema.model.Seat;
import com.cinema.model.Theater;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.TheaterRepository;
import com.cinema.util.ScannerUtils;

import java.util.List;
import java.util.Scanner;

public class TheaterUI {

    private final TheaterRepository theaterRepository;
    private final SeatRepository seatRepository;
    private final Scanner scanner;

    public TheaterUI(TheaterRepository theaterRepository, SeatRepository seatRepository, Scanner scanner) {
        this.theaterRepository = theaterRepository;
        this.seatRepository = seatRepository;
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- THEATER MANAGER ---");
            System.out.println("1. Add new theater");
            System.out.println("2. Edit theater");
            System.out.println("3. Delete theater");
            System.out.println("4. View theater list");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");
            int choice = ScannerUtils.readInt(scanner);
            scanner.nextLine();

            switch (choice) {
                case 1 -> addTheater();
                case 2 -> updateTheater();
                case 3 -> deleteTheater();
                case 4 -> listTheaters();
                case 5 -> { return; }
                default -> System.out.println("Your choice invalid.");
            }

        }
    }

    private void listTheaters() {
        List<Theater> theaters = theaterRepository.findAll();
        if (theaters.isEmpty()) {
            System.out.println("No theaters exists.");
            return;
        }
        System.out.println("\nList of theaters:");
        System.out.printf("%-5s | %-20s | %-8s | %8s%n", "ID", "Name", "Rows", "Columns");
        for (Theater theater : theaters) {
            System.out.printf("%-5s | %-20s | %-8s | %8s%n", theater.getId(), theater.getName(), theater.getTotalRows(), theater.getTotalColumns());
        }
    }

    private void addTheater() {
        System.out.print("Enter theater ID: ");
        int id = ScannerUtils.readInt(scanner);
        scanner.nextLine();
        System.out.print("Enter theater name: ");
        String name = scanner.nextLine();
        System.out.print("Enter total rows: ");
        int rows = ScannerUtils.readInt(scanner);
        scanner.nextLine();
        System.out.print("Enter total columns: ");
        int columns = ScannerUtils.readInt(scanner);
        scanner.nextLine();

        if (theaterRepository.findById(id) != null) {
            System.out.println("ID already exists.");
            return;
        }
        Theater theater = new Theater(id, name, rows, columns);
        theaterRepository.save(theater);

        initializeSeats(theater);
        System.out.println("Added theater successfully and init seats.");
    }

    private void updateTheater() {
        listTheaters();
        System.out.print("Enter theater ID to edit: ");
        int id = ScannerUtils.readInt(scanner);
        scanner.nextLine();
        Theater existing = theaterRepository.findById(id);
        if (existing == null) {
            System.out.println("No such theater exists.");
            return;
        }
        System.out.print("Enter new theater name (leave blank and keep): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) existing.setName(name);

        System.out.print("Enter new total rows (enter 0 to keep it): ");
        int rows = ScannerUtils.readInt(scanner);
        scanner.nextLine();
        if (rows > 0) existing.setTotalRows(rows);
        System.out.print("Enter new total columns (enter 0 to keep it): ");
        int cols = ScannerUtils.readInt(scanner);
        scanner.nextLine();
        if (cols > 0) existing.setTotalColumns(cols);
        theaterRepository.save(existing);
        System.out.println("Updated theater successfully.");
    }

    private void deleteTheater() {
        listTheaters();
        System.out.print("Enter theater ID to delete: ");
        int id = ScannerUtils.readInt(scanner);
        scanner.nextLine();
        theaterRepository.delete(id);

        List<Seat> seats = seatRepository.findByTheaterId(id);
        for (Seat seat : seats) {
            seatRepository.delete(seat.getId());
        }
        System.out.println("Deleted theater and related seats successfully.");
    }

    private void initializeSeats(Theater theater) {
        int theaterId = theater.getId();
        int rows = theater.getTotalRows();
        int columns = theater.getTotalColumns();

        int maxId = seatRepository.findAll().stream().mapToInt(Seat::getId).max().orElse(0);
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= columns; c++) {
                maxId++;
                Seat seat = new Seat(maxId, theaterId, r, c);
                seatRepository.save(seat);
            }
        }
        System.out.println("Created " + (rows * columns) + " seats for theater " + theater.getName());
    }
}
