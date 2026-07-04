package com.cinema.service;

import com.cinema.exception.SeatUnavailableException;
import com.cinema.factory.BookingFactory;
import com.cinema.model.Booking;
import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.repository.BookingRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class BookingManager {

    private final BookingRepository bookingRepository;
    private final BookingFactory bookingFactory;
    private final List<Booking> pendingBookings = Collections.synchronizedList(new ArrayList<>());
    private final ReentrantLock lock = new ReentrantLock(true);
    private BookingPriceService bookingPriceService;

    public BookingManager(BookingRepository bookingRepository, BookingFactory bookingFactory, BookingPriceService bookingPriceService) {
        this.bookingRepository = bookingRepository;
        this.bookingFactory = bookingFactory;
        this.bookingPriceService = bookingPriceService;
    }

    public Booking addPendingBooking(Showtime showtime, Seat seat, String customerName) {
        lock.lock();
        try {
            boolean booked = bookingRepository.isSeatBooked(showtime.getId(), seat.getId())
                    || pendingBookings.stream().anyMatch(b -> b.getShowtimeId() == showtime.getId()
                    && b.getSeatId() == seat.getId());

            if (booked) {
                throw new SeatUnavailableException("Seat" + seat.getId() + " is already booked.");
            }

            double price = bookingPriceService.calculatePrice(showtime, seat);

            Booking booking = bookingFactory.createBooking(showtime.getId(), seat.getId(), customerName, price);

            pendingBookings.add(booking);

            return booking;
        } finally {
            lock.unlock();
        }
    }

    public synchronized void flushBookings() {

        if (!pendingBookings.isEmpty()) {
            List<Booking> toSave = new ArrayList<>(pendingBookings);

            bookingRepository.saveAll(toSave);

            pendingBookings.clear();

            System.out.println("💾 Flushed " + toSave.size() + " bookings to file.");
        }
    }
}
