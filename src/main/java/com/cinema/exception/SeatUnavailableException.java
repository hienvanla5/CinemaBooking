package com.cinema.exception;

public class SeatUnavailableException extends BookingAppException{

    public SeatUnavailableException(String message) {
        super(message);
    }
}
