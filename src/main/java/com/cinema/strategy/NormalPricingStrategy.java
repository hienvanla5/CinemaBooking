package com.cinema.strategy;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;

public class NormalPricingStrategy implements PricingStrategy {
    private static final double BASE_PRICE = 50000;

    @Override
    public double calculatePrice(Showtime showtime, Seat seat) {
        return BASE_PRICE;
    }
}
