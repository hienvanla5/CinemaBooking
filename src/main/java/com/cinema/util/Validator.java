package com.cinema.util;

import com.cinema.exception.InvalidInputException;
import com.cinema.model.Seat;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowtimeRepository;

public class Validator {

    public static void validateCustomerName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be blank.");
        }
    }

    public static void validateSeatId(int seatId, int maxSeats) throws InvalidInputException {
        if (seatId < 1) {
            throw new InvalidInputException("Seat ID must be at least 1.");
        }
        if (seatId > maxSeats) {
            throw new InvalidInputException("Seat ID must not exceed " + maxSeats + ".");
        }
    }

    public static void validateShowtime(int showtimeId, ShowtimeRepository showtimeRepo) {
        if (showtimeRepo.findById(showtimeId) == null) {
            throw new InvalidInputException("Showtime ID " + showtimeId + " does not exist.");
        }
    }

    public static void validateSeatInTheater(int seatId, int theaterId, SeatRepository seatRepo) {
        Seat seat = seatRepo.findById(seatId);
        if (seat == null) {
            throw new InvalidInputException("Seat ID " + seatId + " does not exist.");
        }
        if (seat.getTheaterId() != theaterId) {
            throw new InvalidInputException("Seat " + seatId + " does not belong to theater " + theaterId);
        }
    }
}
