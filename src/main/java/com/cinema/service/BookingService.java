package com.cinema.service;

import com.cinema.exception.InvalidInputException;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowtimeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;

    private final BookingValidator bookingValidator;
    private final BookingManager bookingManager;

    public BookingService(BookingRepository bookingRepository, ShowtimeRepository showtimeRepository, SeatRepository seatRepository, BookingValidator bookingValidator, BookingManager bookingManager) {
        this.bookingRepository = bookingRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;

        this.bookingValidator = bookingValidator;
        this.bookingManager = bookingManager;
    }

    public Booking bookSeat(int showtimeId, int seatId, String customerName)
            throws InvalidInputException, SeatUnavailableException {

        Showtime showtime = bookingValidator.validateBooking(showtimeId, seatId, customerName);

        Seat seat = seatRepository.findById(seatId);

        return bookingManager.addPendingBooking(showtime, seat, customerName);
    }

    public void flushBookings() {
        bookingManager.flushBookings();
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
