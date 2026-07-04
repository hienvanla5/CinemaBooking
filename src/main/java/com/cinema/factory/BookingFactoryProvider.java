package com.cinema.factory;

public class BookingFactoryProvider {

    public static BookingFactory getFactory(String customerType) {
        switch (customerType.toLowerCase()) {
            case "vip":
                return new VIPBookingFactory(1, 10.0);
            case "gold":
                return new VIPBookingFactory(2, 20.0);
            case "platinum":
                return new VIPBookingFactory(3, 30.0);
            default:
                return new RegularBookingFactory();
        }
    }
}
