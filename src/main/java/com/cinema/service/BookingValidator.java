package com.cinema.service;

import com.cinema.exception.InvalidInputException;
import com.cinema.model.Showtime;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowtimeRepository;
import com.cinema.util.Validator;

/**
 * Validates booking requests before a booking is created.
 * <p>
 * This class verifies customer information, ensures the requested
 * showtime exists, and checks that the selected seat belongs to
 * the correct theater.
 */
public class BookingValidator {

    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;

    /**
     * Creates a booking validator.
     *
     * @param showtimeRepository the repository used to validate showtimes
     * @param seatRepository the repository used to validate seats
     */
    public BookingValidator(ShowtimeRepository showtimeRepository,
                            SeatRepository seatRepository) {
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
    }

    /**
     * Validates a booking request.
     * <p>
     * This method performs the following validations:
     * <ul>
     *     <li>Checks that the customer name is valid.</li>
     *     <li>Checks that the specified showtime exists.</li>
     *     <li>Checks that the selected seat belongs to the theater where the showtime is held.</li>
     * </ul>
     *
     * @param showtimeId the ID of the requested showtime
     * @param seatId the ID of the requested seat
     * @param customerName the customer's name
     * @return the validated {@link Showtime}
     * @throws InvalidInputException if any validation fails
     */
    public Showtime validateBooking(int showtimeId, int seatId, String customerName)
            throws InvalidInputException {

        Validator.validateCustomerName(customerName);

        Showtime showtime = showtimeRepository.findById(showtimeId);
        Validator.validateShowtime(showtimeId, showtimeRepository);

        Validator.validateSeatInTheater(
                seatId,
                showtime.getTheaterId(),
                seatRepository
        );

        return showtime;
    }
}