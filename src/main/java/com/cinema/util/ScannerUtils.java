package com.cinema.util;

import java.util.Scanner;

public class ScannerUtils {

    public static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.next().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
