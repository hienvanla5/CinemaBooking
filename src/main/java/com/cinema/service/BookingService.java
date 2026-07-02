package com.cinema.service;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowtimeRepository;
import com.cinema.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingService {

    private final BookingRepository bookingRepository;
    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;

    public BookingService() {
        this(new BookingRepository(), new MovieRepository(), new ShowtimeRepository(), new SeatRepository());
    }

    public BookingService(BookingRepository bookingRepository, MovieRepository movieRepository, ShowtimeRepository showtimeRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
    }

    public Booking bookSeat(int showtimeId, int seatId, String customerName) throws InvalidInputException, SeatUnavailableException {

        Validator.validateCustomerName(customerName);

        Showtime showtime = showtimeRepository.findById(showtimeId);
        Validator.validateShowtime(showtimeId, showtimeRepository);

        int theaterId = showtime.getTheaterId();

        Validator.validateSeatInTheater(seatId, theaterId, seatRepository);

        if (bookingRepository.isSeatBooked(showtimeId, seatId)) {
            throw new SeatUnavailableException("Seat " + seatId + " is already booked.");
        }

        Booking booking = new Booking(showtimeId, seatId, customerName);
        bookingRepository.save(booking);
        return booking;
    }

    public List<Seat> getAvailableSeats(int showtimeId) {
        Showtime showtime = showtimeRepository.findById(showtimeId);
        if (showtime == null) return new ArrayList<>();
        int theaterId = showtime.getTheaterId();

        List<Seat> allSeats = seatRepository.findByTheaterId(theaterId);
        List<Integer> bookedSeatIds = bookingRepository.getBookedSeatsByShowtime(showtimeId);

        return allSeats.stream()
                .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                .collect(Collectors.toList());
    }
}
