package com.cinema.strategy;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;

public class PriceCalculator {

    private final PricingStrategy strategy;

    public PriceCalculator(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculatePrice(Showtime showtime, Seat seat) {
        return strategy.calculatePrice(showtime, seat);
    }
}
