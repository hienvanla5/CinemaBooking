package com.cinema.model;

public class Seat {

    private int id;

    private boolean booked;

    public Seat(int id) {
        this.id = id;
        this.booked = false;
    }

    public int getId() {
        return id;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", booked=" + booked +
                '}';
    }
}
