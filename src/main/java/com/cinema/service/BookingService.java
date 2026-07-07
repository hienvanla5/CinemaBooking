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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Facade service that coordinates the movie ticket booking process.
 * <p>
 * This service delegates validation, booking management, and persistence
 * to specialized components. It provides a simplified API for booking seats,
 * flushing pending bookings, and retrieving available seats.
 */
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;

    private final BookingValidator bookingValidator;
    private final BookingManager bookingManager;

    /**
     * Creates a booking service with the required repositories and service components.
     *
     * @param bookingRepository the repository used to access booking data
     * @param showtimeRepository the repository used to access showtime data
     * @param seatRepository the repository used to access seat data
     * @param bookingValidator the component responsible for validating booking requests
     * @param bookingManager the component responsible for creating and managing bookings
     */
    public BookingService(BookingRepository bookingRepository,
                          ShowtimeRepository showtimeRepository,
                          SeatRepository seatRepository,
                          BookingValidator bookingValidator,
                          BookingManager bookingManager) {

        this.bookingRepository = bookingRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;

        this.bookingValidator = bookingValidator;
        this.bookingManager = bookingManager;
    }

    /**
     * Books a seat for a customer.
     * <p>
     * The booking request is first validated. If the request is valid,
     * a new booking is created and added to the pending booking list.
     *
     * @param showtimeId the ID of the selected showtime
     * @param seatId the ID of the selected seat
     * @param customerName the customer's name
     * @return the newly created booking
     * @throws InvalidInputException if the booking request contains invalid data
     * @throws SeatUnavailableException if the requested seat has already been booked
     */
    public Booking bookSeat(int showtimeId, int seatId, String customerName)
            throws InvalidInputException, SeatUnavailableException {

        Showtime showtime = bookingValidator.validateBooking(showtimeId, seatId, customerName);

        Seat seat = seatRepository.findById(seatId);

        return bookingManager.addPendingBooking(showtime, seat, customerName);
    }

    /**
     * Persists all pending bookings to the repository.
     * <p>
     * This method delegates the operation to {@link BookingManager}.
     */
    public void flushBookings() {
        bookingManager.flushBookings();
    }

    /**
     * Returns all available seats for the specified showtime.
     * <p>
     * A seat is considered available if it belongs to the theater of the
     * specified showtime and has not been booked.
     *
     * @param showtimeId the ID of the showtime
     * @return a list of available seats; returns an empty list if the showtime does not exist
     */
    public List<Seat> getAvailableSeats(int showtimeId) {
        Showtime showtime = showtimeRepository.findById(showtimeId);

        if (showtime == null) {
            return new ArrayList<>();
        }

        int theaterId = showtime.getTheaterId();

        List<Seat> allSeats = seatRepository.findByTheaterId(theaterId);
        List<Integer> bookedSeatIds = bookingRepository.getBookedSeatsByShowtime(showtimeId);

        return allSeats.stream()
                .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                .collect(Collectors.toList());
    }

    public double getTotalRevenue() {
        return bookingRepository.findAll().stream()
                .mapToDouble(Booking::getPrice)
                .sum();
    }

    public Map<String, Double> getRevenueByMovie() {
        // join với showtime và movie để lấy tên phim
        // rồi chỉ cần tính theo showtime ID
        return bookingRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        b -> String.valueOf(b.getShowtimeId()),
                        Collectors.summingDouble(Booking::getPrice)
                ));
    }
}