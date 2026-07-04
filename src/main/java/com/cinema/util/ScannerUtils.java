package com.cinema.util;

import java.util.Scanner;

/**
 * Utility class that provides helper methods for reading user input.
 */
public class ScannerUtils {

    private static final AppLogger logger = AppLogger.getInstance();

    /**
     * Reads an integer value from the specified {@link Scanner}.
     * <p>
     * If the input is not a valid integer, the user is prompted to
     * enter another value until a valid integer is provided.
     *
     * @param scanner the {@code Scanner} used to read user input
     * @return the integer entered by the user
     */
    public static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.next().trim());
            } catch (NumberFormatException e) {
                logger.warning("Please enter a valid number.");
            }
        }
    }
}