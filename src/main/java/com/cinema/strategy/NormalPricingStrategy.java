package com.cinema.strategy;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.util.AppConstants;

/**
 * Default pricing strategy for movie tickets.
 * <p>
 * This strategy always returns the standard ticket price,
 * regardless of the showtime or seat.
 */
public class NormalPricingStrategy implements PricingStrategy {

    /**
     * Returns the standard ticket price.
     *
     * @param showtime the selected showtime (currently not used)
     * @param seat the selected seat (currently not used)
     * @return the base ticket price
     */
    @Override
    public double calculatePrice(Showtime showtime, Seat seat) {
        return AppConstants.BASE_TICKET_PRICE;
    }
}