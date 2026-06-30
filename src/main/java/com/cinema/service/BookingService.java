package com.cinema.service;

import com.cinema.model.Booking;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.MovieRepository;

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

    public boolean bookSeat(int movieId, int seatId, String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            System.out.println("⚠️ Customer name is not empty.");
            return false;
        }
        if (seatId < 1 || seatId > 10) {
            System.out.println("⚠️ Seat number is not valid (1-10).");
            return false;
        }

        if (movieRepository.findById(movieId) == null) {
            System.out.println("⚠️ This movie is not exist.");
            return false;
        }

        if (bookingRepository.isSeatBooked(movieId, seatId)) {
            System.out.println("❌ Seat " + seatId + " is booked.");
            return false;
        }

        Booking booking = new Booking(movieId, seatId, customerName);
        bookingRepository.save(booking);
        System.out.println("✅ Booked ticket for " + customerName + " (Movie ID " + movieId + ", Seat " + seatId + ")");
        return true;
    }


}
