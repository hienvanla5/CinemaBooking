package com.cinema.service;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.MovieRepository;
import com.cinema.util.Validator;

public class BookingService {

    private final BookingRepository bookingRepository;
    private final MovieRepository movieRepository;

    public BookingService() {
        this(new BookingRepository(), new MovieRepository());
    }

    public BookingService(BookingRepository bookingRepository, MovieRepository movieRepository) {
        this.bookingRepository = bookingRepository;
        this.movieRepository = movieRepository;
    }

    public boolean bookSeat(int movieId, int seatId, String customerName) throws InvalidInputException, SeatUnavailableException {

        Validator.validateCustomerName(customerName);

        Validator.validateSeatId(seatId, 10);

        if (movieRepository.findById(movieId) == null) {
            throw new InvalidInputException("Movie ID does not exist.");
        }

        if (bookingRepository.isSeatBooked(movieId, seatId)) {
            throw new SeatUnavailableException("Seat " + seatId + " is already booked.");
        }

        Booking booking = new Booking(movieId, seatId, customerName);
        bookingRepository.save(booking);
        System.out.println("✅ Booked ticket for " + customerName + " (Movie ID " + movieId + ", Seat " + seatId + ")");
        return true;
    }


}
