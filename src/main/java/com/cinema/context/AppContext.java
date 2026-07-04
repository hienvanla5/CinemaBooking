package com.cinema.context;

import com.cinema.factory.BookingFactory;
import com.cinema.factory.RegularBookingFactory;
import com.cinema.repository.*;
import com.cinema.service.BookingManager;
import com.cinema.service.BookingPriceService;
import com.cinema.service.BookingService;
import com.cinema.service.BookingValidator;

/**
 * Singleton application context responsible for creating and managing
 * shared instances of repositories, services, and factories.
 * <p>
 * This class performs manual dependency injection, ensuring that
 * application components share the same instances throughout the
 * application's lifecycle.
 */
public class AppContext {

    private static final AppContext INSTANCE = new AppContext();

    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;
    private final BookingRepository bookingRepository;

    private final BookingFactory bookingFactory;

    private final BookingValidator bookingValidator;
    private final BookingPriceService bookingPriceService;
    private final BookingManager bookingManager;
    private final BookingService bookingService;

    /**
     * Creates and initializes all application components.
     * <p>
     * This constructor is private to enforce the Singleton pattern.
     * All repositories, factories, and services are created once and
     * reused during the application's lifetime.
     */
    private AppContext() {

        movieRepository = new MovieRepository();
        theaterRepository = new TheaterRepository();
        seatRepository = new SeatRepository();
        showtimeRepository = new ShowtimeRepository();
        bookingRepository = new BookingRepository();

        bookingFactory = new RegularBookingFactory();

        bookingValidator = new BookingValidator(showtimeRepository, seatRepository);
        bookingPriceService = new BookingPriceService();
        bookingManager = new BookingManager(
                bookingRepository,
                bookingFactory,
                bookingPriceService,
                true
        );

        bookingService = new BookingService(
                bookingRepository,
                showtimeRepository,
                seatRepository,
                bookingValidator,
                bookingManager
        );
    }

    public static AppContext getInstance() {
        return INSTANCE;
    }

    public BookingService getBookingService() {
        return bookingService;
    }
}