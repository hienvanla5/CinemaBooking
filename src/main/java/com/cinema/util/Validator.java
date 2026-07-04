package com.cinema.util;

import com.cinema.exception.InvalidInputException;
import com.cinema.model.Seat;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowtimeRepository;

/**
 * Utility class that provides common validation methods for booking-related
 * operations.
 * <p>
 * All validation methods throw {@link InvalidInputException} if the input
 * data is invalid.
 */
public class Validator {

    /**
     * Validates a customer's name.
     *
     * @param name the customer name to validate
     * @throws InvalidInputException if the name is {@code null}, empty,
     *                               or contains only whitespace
     */
    public static void validateCustomerName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be blank.");
        }
    }

    /**
     * Validates whether a seat ID is within the valid range.
     *
     * @param seatId   the seat ID to validate
     * @param maxSeats the maximum allowed seat ID
     * @throws InvalidInputException if the seat ID is less than 1 or
     *                               greater than the maximum allowed value
     */
    public static void validateSeatId(int seatId, int maxSeats) throws InvalidInputException {
        if (seatId < 1) {
            throw new InvalidInputException("Seat ID must be at least 1.");
        }
        if (seatId > maxSeats) {
            throw new InvalidInputException("Seat ID must not exceed " + maxSeats + ".");
        }
    }

    /**
     * Validates that a showtime exists.
     *
     * @param showtimeId  the ID of the showtime to validate
     * @param showtimeRepo the repository used to look up showtimes
     * @throws InvalidInputException if no showtime exists with the specified ID
     */
    public static void validateShowtime(int showtimeId, ShowtimeRepository showtimeRepo) {
        if (showtimeRepo.findById(showtimeId) == null) {
            throw new InvalidInputException("Showtime ID " + showtimeId + " does not exist.");
        }
    }

    /**
     * Validates that a seat exists and belongs to the specified theater.
     *
     * @param seatId     the seat ID to validate
     * @param theaterId  the expected theater ID
     * @param seatRepo   the repository used to look up seats
     * @throws InvalidInputException if the seat does not exist or
     *                               does not belong to the specified theater
     */
    public static void validateSeatInTheater(int seatId, int theaterId, SeatRepository seatRepo) {
        Seat seat = seatRepo.findById(seatId);

        if (seat == null) {
            throw new InvalidInputException("Seat ID " + seatId + " does not exist.");
        }

        if (seat.getTheaterId() != theaterId) {
            throw new InvalidInputException(
                    "Seat " + seatId + " does not belong to theater " + theaterId + "."
            );
        }
    }
}