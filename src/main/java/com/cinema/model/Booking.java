package com.cinema.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {

    private int showtimeId;
    private int seatId;
    private String customerName;
    private String bookingTime;

    public Booking(int showtimeId, int seatId, String customerName) {
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.customerName = customerName;
        this.bookingTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Booking(int showtimeId, int seatId, String customerName, String bookingTime) {
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.customerName = customerName;
        this.bookingTime = bookingTime;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public int getSeatId() {
        return seatId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "showtimeId=" + showtimeId +
                ", seatId=" + seatId +
                ", customerName='" + customerName + '\'' +
                ", bookingTime='" + bookingTime + '\'' +
                '}';
    }
}
