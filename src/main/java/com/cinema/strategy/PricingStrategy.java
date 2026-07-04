package com.cinema.strategy;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;

/**
 * Defines the contract for ticket pricing strategies.
 * <p>
 * Implementations of this interface provide different pricing rules,
 * such as normal pricing, gold-hour pricing, or weekend pricing.
 * This interface is the Strategy in the Strategy design pattern.
 */
public interface PricingStrategy {

    /**
     * Calculates the ticket price for the specified showtime and seat.
     *
     * @param showtime the selected showtime
     * @param seat the selected seat
     * @return the calculated ticket price
     */
    double calculatePrice(Showtime showtime, Seat seat);
}