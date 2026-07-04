package com.cinema.service;

import com.cinema.factory.BookingFactory;
import com.cinema.factory.RegularBookingFactory;
import com.cinema.model.Booking;
import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.repository.BookingRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class BookingManagerTest {

    @Test
    void testBookingManager_BookSeat_Success() throws Exception {
        BookingRepository repo = mock(BookingRepository.class);
        BookingPriceService priceService = mock(BookingPriceService.class);

        when(repo.isSeatBooked(anyInt(), anyInt())).thenReturn(false);
        when(priceService.calculatePrice(any(), any())).thenReturn(50000.0);

        BookingFactory factory = new RegularBookingFactory();
        BookingManager manager = new BookingManager(repo, factory, priceService);

        Showtime showtime = new Showtime(1, 1, 1, LocalDateTime.now().toString());
        Seat seat = new Seat(5, 1, 0, 4);

        Booking booking = manager.addPendingBooking(showtime, seat, "Alice");

        assertNotNull(booking);
        assertEquals(1, booking.getShowtimeId());
        assertEquals(5, booking.getSeatId());
        assertEquals("Alice", booking.getCustomerName());
        assertEquals(50000.0, booking.getPrice());
    }
}
