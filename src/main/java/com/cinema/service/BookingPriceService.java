package com.cinema.service;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.strategy.NormalPricingStrategy;
import com.cinema.strategy.PriceCalculator;

/**
 * Service responsible for calculating ticket prices.
 * <p>
 * This service delegates price calculation to a {@link PriceCalculator},
 * allowing different pricing strategies to be applied without changing
 * the booking logic.
 */
public class BookingPriceService {

    private final PriceCalculator priceCalculator;

    /**
     * Creates a booking price service using the default
     * {@link NormalPricingStrategy}.
     */
    public BookingPriceService() {
        this(new PriceCalculator(new NormalPricingStrategy()));
    }

    /**
     * Creates a booking price service with the specified price calculator.
     *
     * @param priceCalculator the price calculator used to calculate ticket prices
     */
    public BookingPriceService(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    /**
     * Calculates the ticket price for the specified showtime and seat.
     *
     * @param showtime the selected showtime
     * @param seat the selected seat
     * @return the calculated ticket price
     */
    public double calculatePrice(Showtime showtime, Seat seat) {
        return priceCalculator.calculatePrice(showtime, seat);
    }
}