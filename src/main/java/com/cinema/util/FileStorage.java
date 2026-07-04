package com.cinema.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {

    private static final FileStorage INSTANCE = new FileStorage();

    private FileStorage() {
    }

    public static FileStorage getInstance() {
        return INSTANCE;
    }

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
