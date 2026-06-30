package com.cinema.util;

import com.cinema.exception.InvalidInputException;

public class Validator {

    public static void validateCustomerName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be blank.");
        }
    }

    public static void validateSeatId(int seatId, int maxSeats) {
        if (seatId < 1 || seatId > maxSeats) {
            throw new InvalidInputException("Seat number must be between 1 and " + maxSeats + ".");
        }
    }
}
