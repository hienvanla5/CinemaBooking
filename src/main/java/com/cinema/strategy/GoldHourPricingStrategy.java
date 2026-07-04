package com.cinema.strategy;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.util.AppConstants;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Pricing strategy that applies a special ticket price during
 * the Gold Hour period.
 * <p>
 * If the showtime starts between 6:00 PM and 10:00 PM
 * (exclusive), the gold-hour ticket price is applied.
 * Otherwise, the standard ticket price is used.
 */
public class GoldHourPricingStrategy implements PricingStrategy {

    private static final LocalTime GOLD_HOUR_START = LocalTime.of(18, 0);
    private static final LocalTime GOLD_HOUR_END = LocalTime.of(22, 0);

    /**
     * Calculates the ticket price based on the showtime.
     * <p>
     * A showtime starting during the Gold Hour period receives
     * the gold-hour price; otherwise, the base ticket price is returned.
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

        LocalTime time = startTime.toLocalTime();

        if (time.isAfter(GOLD_HOUR_START) && time.isBefore(GOLD_HOUR_END)) {
            return AppConstants.GOLD_HOUR_PRICE;
        }

        return AppConstants.BASE_TICKET_PRICE;
    }
}