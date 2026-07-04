package com.cinema.service;

import com.cinema.exception.InvalidInputException;
import com.cinema.model.Showtime;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowtimeRepository;
import com.cinema.util.Validator;

public class BookingValidator {

    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;

    public BookingValidator(ShowtimeRepository showtimeRepository, SeatRepository seatRepository) {
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
    }

    public Showtime validateBooking(int showtimeId, int seatId, String customerName) throws InvalidInputException {
        Validator.validateCustomerName(customerName);

        Showtime showtime = showtimeRepository.findById(showtimeId);
        Validator.validateShowtime(showtimeId, showtimeRepository);

        Validator.validateSeatInTheater(seatId, showtime.getTheaterId(), seatRepository);

        return showtime;
    }
}
