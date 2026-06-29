package com.cinema.model;

public class Booking {

    private int movieId;

    private int seatId;

    private String customerName;

    public Booking(int movieId, int seatId, String customerName) {
        this.movieId = movieId;
        this.seatId = seatId;
        this.customerName = customerName;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getSeatId() {
        return seatId;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "movieId=" + movieId +
                ", seatId=" + seatId +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
