package com.cinema.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.cinema.util.FileStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class FileStorageTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadAndWrite() throws IOException {
        String testFile = tempDir.resolve("test.txt").toString();

        List<String> expected = Arrays.asList("Dong 1", "Dong 2", "Dong 3");
        FileStorage.getInstance().writeLines(testFile, expected);

        List<String> actual = FileStorage.getInstance().readLines(testFile);
        assertEquals(expected, actual, "The content must be match.");
    }

    @Test
    void testReadNonExistentFile() {
        assertThrows(IOException.class, () -> {
            FileStorage.getInstance().readLines("non-existent.txt");
        });
    }
}
