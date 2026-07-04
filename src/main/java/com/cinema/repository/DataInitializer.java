package com.cinema.repository;

import com.cinema.util.FileStorage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DataInitializer {

    private static final String MOVIES_FILE = "src/main/resources/data/movies.csv";

    public static void createSampleMovies() throws IOException {
        List<String> movieLines = Arrays.asList(
                "1|Avengers: Endgame|180",
                "2|Titanic|195",
                "3|Inception|148"
        );
        FileStorage.getInstance().writeLines(MOVIES_FILE, movieLines);
        System.out.println("✅ Create sample file at: " + MOVIES_FILE);
    }

    public static void main(String[] args) {
        try {
            createSampleMovies();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
