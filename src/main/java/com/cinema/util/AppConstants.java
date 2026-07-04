package com.cinema.util;

public class AppConstants {

    public static final int DEFAULT_MAX_SEATS = 10;
    public static final String MOVIES_FILE = "src/main/resources/data/movies.csv";
    public static final String BOOKINGS_FILE = "src/main/resources/data/bookings.csv";
    public static final String THEATERS_FILE = "src/main/resources/data/theaters.csv";
    public static final String SHOWTIMES_FILE = "src/main/resources/data/showtimes.csv";
    public static final String SEATS_FILE = "src/main/resources/data/seats.csv";

    public static final double BASE_TICKET_PRICE = 50000.0;
    public static final double GOLD_HOUR_PRICE = 75000;
    public static final double WEEKEND_SURCHARGE = 1.1;

    public static final int DEFAULT_TOTAL_ROWS = 5;
    public static final int DEFAULT_TOTAL_COLUMNS = 5;

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String GOLD_HOUR_START = "18:00";
    public static final String GOLD_HOUR_END = "22:00";

    public static final int MIN_SEAT_ID = 1;
    public static final int MAX_SEATS_PER_THEATER = 50;

    public static final int DEFAULT_THREAD_POOL_SIZE = 10;
}
