package com.cinema.strategy;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GoldHourPricingStrategy implements PricingStrategy {
    private static final double BASE_PRICE = 50000;
    private static final double GOLD_HOUR_PRICE = 75000;
    private static final LocalTime GOLD_HOUR_START = LocalTime.of(18, 0);
    private static final LocalTime GOLD_HOUR_END = LocalTime.of(22, 0);

    @Override
    public double calculatePrice(Showtime showtime, Seat seat) {
        LocalDateTime startTime = LocalDateTime.parse(showtime.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalTime time = startTime.toLocalTime();

        if (time.isAfter(GOLD_HOUR_START) && time.isBefore(GOLD_HOUR_END)) {
            return GOLD_HOUR_PRICE;
        }
        return BASE_PRICE;
    }
}
