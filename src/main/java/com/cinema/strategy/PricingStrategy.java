package com.cinema.strategy;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;

public interface PricingStrategy {

    double calculatePrice(Showtime showtime, Seat seat);
}
