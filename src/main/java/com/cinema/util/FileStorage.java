package com.cinema.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton utility class for reading from and writing to text files.
 * <p>
 * This class provides simple methods to read all lines from a file
 * and write a list of lines to a file. Parent directories are created
 * automatically if they do not already exist.
 */
public class FileStorage {

    private static final FileStorage INSTANCE = new FileStorage();

    /**
     * Prevents external instantiation.
     */
    private FileStorage() {
    }

    /**
     * Returns the singleton instance of {@code FileStorage}.
     *
     * @return the singleton {@code FileStorage} instance
     */
    public static FileStorage getInstance() {
        return INSTANCE;
    }

    /**
     * Reads all lines from the specified file.
     *
     * @param filePath the path to the file
     * @return a list containing all lines in the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<String> readLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Writes the given lines to the specified file.
     * <p>
     * If the parent directories do not exist, they are created
     * automatically. Any existing file content is overwritten.
     *
     * @param filePath the path to the output file
     * @param lines the lines to write
     * @throws IOException if an I/O error occurs while writing the file
     */
    public void writeLines(String filePath, List<String> lines) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}