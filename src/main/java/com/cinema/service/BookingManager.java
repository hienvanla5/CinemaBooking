package com.cinema.service;

import com.cinema.exception.SeatUnavailableException;
import com.cinema.factory.BookingFactory;
import com.cinema.model.Booking;
import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.repository.BookingRepository;
import com.cinema.util.AppLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Manages the booking process before bookings are persisted.
 * <p>
 * This class is responsible for preventing duplicate bookings,
 * calculating ticket prices, creating booking objects, and temporarily
 * storing bookings until they are flushed to the repository.
 * Thread safety is ensured using a {@link ReentrantLock}.
 */
public class BookingManager {

    private final BookingRepository bookingRepository;
    private final BookingFactory bookingFactory;
    private final List<Booking> pendingBookings = Collections.synchronizedList(new ArrayList<>());
    private final ReentrantLock lock = new ReentrantLock(true);
    private BookingPriceService bookingPriceService;
    private static final AppLogger logger = AppLogger.getInstance();
    private final boolean immediatePersist;

    /**
     * Creates a booking manager.
     *
     * @param bookingRepository the repository used to check and persist bookings
     * @param bookingFactory the factory used to create booking objects
     * @param bookingPriceService the service used to calculate ticket prices
     */
    public BookingManager(BookingRepository bookingRepository,
                          BookingFactory bookingFactory,
                          BookingPriceService bookingPriceService, boolean immediatePersist) {
        this.bookingRepository = bookingRepository;
        this.bookingFactory = bookingFactory;
        this.bookingPriceService = bookingPriceService;
        this.immediatePersist = immediatePersist;
    }

    /**
     * Creates a pending booking if the requested seat is available.
     * <p>
     * The booking process is protected by a lock to ensure that multiple
     * threads cannot reserve the same seat simultaneously.
     *
     * @param showtime the selected showtime
     * @param seat the selected seat
     * @param customerName the customer's name
     * @return the created booking
     * @throws SeatUnavailableException if the seat has already been booked
     */
    public Booking addPendingBooking(Showtime showtime, Seat seat, String customerName) {
        lock.lock();

        try {
            boolean booked = bookingRepository.isSeatBooked(showtime.getId(), seat.getId())
                    || pendingBookings.stream()
                    .anyMatch(b -> b.getShowtimeId() == showtime.getId()
                            && b.getSeatId() == seat.getId());

            if (booked) {
                throw new SeatUnavailableException(
                        "Seat " + seat.getId() + " is already booked."
                );
            }

            double price = bookingPriceService.calculatePrice(showtime, seat);

            Booking booking = bookingFactory.createBooking(
                    showtime.getId(),
                    seat.getId(),
                    customerName,
                    price
            );

            pendingBookings.add(booking);

            if (immediatePersist) {
                flushBookings();
            }

            return booking;

        } finally {
            lock.unlock();
        }
    }

    /**
     * Persists all pending bookings to the repository and clears the
     * pending booking list.
     * <p>
     * If there are no pending bookings, this method does nothing.
     */
    public synchronized void flushBookings() {

        if (!pendingBookings.isEmpty()) {
            List<Booking> toSave = new ArrayList<>(pendingBookings);

            bookingRepository.saveAll(toSave);

            pendingBookings.clear();

            logger.logInfo("💾 Saved " + toSave.size() + " booking(s) to the file.");
        }
    }

    // use for Main - BookingUI
    public Booking bookImmediately(Showtime showtime, Seat seat, String customerName) {

        Booking booking = addPendingBooking(showtime, seat, customerName);
        flushBookings();
        return booking;
    }
}