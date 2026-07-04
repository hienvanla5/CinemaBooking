package com.cinema.strategy;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeekendPricingStrategy implements PricingStrategy {

    private static final double BASE_PRICE = 50000;
    private static final double WEEKEND_SURCHARGE = 1.1;

    @Override
    public double calculatePrice(Showtime showtime, Seat seat) {
        LocalDateTime startTime = LocalDateTime.parse(showtime.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        DayOfWeek day = startTime.getDayOfWeek();

        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return Math.round(BASE_PRICE * WEEKEND_SURCHARGE);
        }
        return BASE_PRICE;
    }
}
