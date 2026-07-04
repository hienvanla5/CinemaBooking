package com.cinema.service;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.strategy.NormalPricingStrategy;
import com.cinema.strategy.PriceCalculator;

public class BookingPriceService {

    private final PriceCalculator priceCalculator;

    public BookingPriceService() {
        this(new PriceCalculator(new NormalPricingStrategy()));
    }

    public BookingPriceService(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    public double calculatePrice(Showtime showtime, Seat seat) {
        return priceCalculator.calculatePrice(showtime, seat);
    }
}
