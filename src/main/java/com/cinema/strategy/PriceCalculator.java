package com.cinema.strategy;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;

/**
 * Calculates ticket prices using a specified pricing strategy.
 * <p>
 * This class serves as the context in the Strategy pattern. It delegates
 * the price calculation to the configured {@link PricingStrategy},
 * allowing pricing rules to be changed without modifying client code.
 */
public class PriceCalculator {

    private final PricingStrategy strategy;

    /**
     * Creates a price calculator with the specified pricing strategy.
     *
     * @param strategy the pricing strategy used to calculate ticket prices
     */
    public PriceCalculator(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Calculates the ticket price for the specified showtime and seat.
     *
     * @param showtime the selected showtime
     * @param seat the selected seat
     * @return the calculated ticket price
     */
    public double calculatePrice(Showtime showtime, Seat seat) {
        return strategy.calculatePrice(showtime, seat);
    }
}