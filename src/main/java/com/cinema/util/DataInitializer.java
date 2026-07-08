package com.cinema.util;

import java.io.IOException;
import java.util.List;

/**
 * Utility class for generating sample data files used by the application.
 * <p>
 * This class creates predefined CSV files that can be used to initialize
 * the application's data during development or testing.
 */
public class DataInitializer {


    public static void initSampleData() throws IOException {

        String basePath = "src/main/resources/data/";

        FileStorage.getInstance().writeLines(basePath + "movies.csv", List.of(
                "1|Avengers: Endgame|180",
                "2|Titanic|195",
                "3|Inception|148"
        ));

        FileStorage.getInstance().writeLines(basePath + "theaters.csv", List.of(
                "1|Hall A|5|5",
                "2|Hall B|4|6"
        ));

        StringBuilder seatsA = new StringBuilder();
        int id = 1;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                seatsA.append(id++).append("|1|").append(row).append("|").append(col).append("\n");
            }
        }

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 6; col++) {
                seatsA.append(id++).append("|2|").append(row).append("|").append(col).append("\n");
            }
        }

        FileStorage.getInstance().writeLines(basePath + "seats.csv", List.of(
                seatsA.toString().split("\n")
        ));

        FileStorage.getInstance().writeLines(basePath + "showtimes.csv", List.of(
                "1|1|1|2026-08-01 10:00",
                "2|1|1|2026-08-01 14:00",
                "3|1|2|2026-08-01 16:30",
                "4|2|1|2026-08-01 18:00",
                "5|3|2|2026-08-02 10:00"
        ));

        FileStorage.getInstance().writeLines(basePath + "bookings.csv", List.of());

        System.out.println("✅ Samples data created successfully!");
    }
}