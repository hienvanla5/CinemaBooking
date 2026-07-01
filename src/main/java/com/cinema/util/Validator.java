package com.cinema.util;

import com.cinema.exception.InvalidInputException;

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
}
