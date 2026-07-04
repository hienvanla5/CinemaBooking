package com.cinema.factory;

/**
 * Provides the appropriate {@link BookingFactory} implementation
 * based on the customer type.
 * <p>
 * Supported customer types:
 * <ul>
 *     <li><b>vip</b> - VIP customer (Level 1, 10% discount)</li>
 *     <li><b>gold</b> - Gold customer (Level 2, 20% discount)</li>
 *     <li><b>platinum</b> - Platinum customer (Level 3, 30% discount)</li>
 *     <li>Any other value - Regular customer (no discount)</li>
 * </ul>
 */
public class BookingFactoryProvider {

    /**
     * Returns a {@link BookingFactory} corresponding to the specified
     * customer type.
     *
     * @param customerType the customer type (e.g. {@code "vip"},
     *                     {@code "gold"}, {@code "platinum"})
     * @return a matching {@link BookingFactory}; returns
     *         {@link RegularBookingFactory} if the customer type is
     *         not recognized
     */
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