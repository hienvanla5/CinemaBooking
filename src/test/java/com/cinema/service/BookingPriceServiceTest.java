package com.cinema.service;

import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.strategy.GoldHourPricingStrategy;
import com.cinema.strategy.NormalPricingStrategy;
import com.cinema.strategy.PriceCalculator;
import static org.junit.jupiter.api.Assertions.*;

import com.cinema.strategy.WeekendPricingStrategy;
import org.junit.jupiter.api.Test;

public class BookingPriceServiceTest {

    @Test
    void testCalculatePrice_NormalStrategy() {
        BookingPriceService service = new BookingPriceService(new PriceCalculator(new NormalPricingStrategy()));

        Showtime showtime = new Showtime(1, 1, 1, "2026-07-15 10:00");
        Seat seat = new Seat(1, 1, 0, 0);

        double price = service.calculatePrice(showtime, seat);

        assertEquals(50000.0, price);
    }

    @Test
    void testCalculatePrice_GoldHourStrategy_InGoldHour() {
        BookingPriceService service = new BookingPriceService(new PriceCalculator(new GoldHourPricingStrategy()));

        Showtime showtime = new Showtime(1, 1, 1, "2026-07-15 20:00");
        Seat seat = new Seat(1, 1, 0, 0);

        double price = service.calculatePrice(showtime, seat);

        assertEquals(75000, price);
    }

    @Test
    void testCalculatePrice_GoldHourStrategy_OutGoldHour() {
        BookingPriceService service = new BookingPriceService(new PriceCalculator(new GoldHourPricingStrategy()));

        Showtime showtime = new Showtime(1, 1, 1, "2026-07-15 23:00");
        Seat seat = new Seat(1, 1, 0, 0);

        double price = service.calculatePrice(showtime, seat);

        assertEquals(50000, price);
    }

    @Test
    void testCalculatePrice_WeekendStrategy_InWeekend() {
        BookingPriceService service = new BookingPriceService(new PriceCalculator(new WeekendPricingStrategy()));

        Showtime showtime = new Showtime(1, 1, 1, "2026-07-05 15:00");
        Seat seat = new Seat(1, 1, 0, 0);

        double price = service.calculatePrice(showtime, seat);

        assertEquals(55000.0, price);
    }

    @Test
    void testCalculatePrice_WeekendStrategy_OutWeekend() {
        BookingPriceService service = new BookingPriceService(new PriceCalculator(new WeekendPricingStrategy()));

        Showtime showtime = new Showtime(1, 1, 1, "2026-07-08 23:00");
        Seat seat = new Seat(1, 1, 0, 0);

        double price = service.calculatePrice(showtime, seat);

        assertEquals(50000, price);
    }
}
