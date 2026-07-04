package com.cinema.strategy;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Pricing strategy that applies a surcharge to tickets purchased for
 * weekend showtimes.
 * <p>
 * If a showtime falls on Saturday or Sunday, a weekend surcharge is
 * applied to the base ticket price. Otherwise, the standard ticket
 * price is returned.
 */
public class WeekendPricingStrategy implements PricingStrategy {

    private static final double BASE_PRICE = 50000;
    private static final double WEEKEND_SURCHARGE = 1.1;

    /**
     * Calculates the ticket price based on the day of the showtime.
     * <p>
     * Showtimes on Saturday or Sunday are charged with a weekend
     * surcharge. Weekday showtimes use the standard ticket price.
     *
     * @param showtime the showtime for which the ticket price is calculated
     * @param seat the selected seat (currently not used in the calculation)
     * @return the calculated ticket price
     */
    @Override
    public double calculatePrice(Showtime showtime, Seat seat) {
        LocalDateTime startTime = LocalDateTime.parse(
                showtime.getStartTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        );

        DayOfWeek day = startTime.getDayOfWeek();

        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return Math.round(BASE_PRICE * WEEKEND_SURCHARGE);
        }

        return BASE_PRICE;
    }
}