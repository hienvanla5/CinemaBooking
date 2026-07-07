package com.cinema.repository;

import com.cinema.util.AppConstants;
import com.cinema.util.AppLogger;
import com.cinema.util.FileStorage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for generating sample data files used by the application.
 * <p>
 * This class creates predefined CSV files that can be used to initialize
 * the application's data during development or testing.
 */
public class DataInitializer {

    private static final AppLogger logger = AppLogger.getInstance();

    /**
     * Creates a sample movie data file containing predefined movie records.
     *
     * @throws IOException if an error occurs while writing the file
     */
    public static void createSampleMovies() throws IOException {
        List<String> movieLines = Arrays.asList(
                "1|Avengers: Endgame|180",
                "2|Titanic|195",
                "3|Inception|148"
        );

        FileStorage.getInstance().writeLines(AppConstants.MOVIES_FILE, movieLines);

        logger.logInfo("✅ Created sample file at: " + AppConstants.MOVIES_FILE);
    }

    /**
     * Runs the data initializer to generate the sample movie file.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            createSampleMovies();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}