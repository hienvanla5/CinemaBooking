package com.cinema.util;

import com.cinema.exception.InvalidInputException;
import com.cinema.model.Seat;
import com.cinema.repository.FileStorage;
import com.cinema.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    private SeatRepository seatRepo;

    @TempDir
    Path tempDir;
    private String seatsFile;

    @BeforeEach
    void setUp() throws IOException {
        seatsFile = tempDir.resolve("seats.csv").toString();
        FileStorage fileStorage = new FileStorage();

        List<String> lines = new ArrayList<>();
        fileStorage.writeLines(seatsFile, lines);

        seatRepo = new SeatRepository(seatsFile);
    }

    @Test
    void testValidateCustomerName_Valid() {
        assertDoesNotThrow(() -> Validator.validateCustomerName("John Doe"));
        assertDoesNotThrow(() -> Validator.validateCustomerName("A"));
    }

    @Test
    void testValidateCustomerName_Invalid() {
        assertThrows(InvalidInputException.class, () -> Validator.validateCustomerName(null));
        assertThrows(InvalidInputException.class, () -> Validator.validateCustomerName(""));
        assertThrows(InvalidInputException.class, () -> Validator.validateCustomerName("      "));
    }

    @Test
    void testValidateSeatId_Valid() {
        assertDoesNotThrow(() -> Validator.validateSeatId(1, 10));
        assertDoesNotThrow(() -> Validator.validateSeatId(5, 10));
        assertDoesNotThrow(() -> Validator.validateSeatId(10, 10));
    }

    @Test
    void testValidateSeatId_Invalid() {
        assertThrows(InvalidInputException.class, () -> Validator.validateSeatId(0, 10));
        assertThrows(InvalidInputException.class, () -> Validator.validateSeatId(-1, 10));
        assertThrows(InvalidInputException.class, () -> Validator.validateSeatId(11, 10));
    }

    @Test
    void testValidateSeatId_WithDifferentMax() {
        assertDoesNotThrow(() -> Validator.validateSeatId(5, 10));
        assertThrows(InvalidInputException.class, () -> Validator.validateSeatId(11, 10));
        assertThrows(InvalidInputException.class, () -> Validator.validateSeatId(0, 10));
    }

    @Test
    void testValidateSeatInTheater() {
        seatRepo.save(new Seat(1, 1, 1, 1));
        assertDoesNotThrow(() -> Validator.validateSeatInTheater(1, 1, seatRepo));
        assertThrows(InvalidInputException.class, () -> Validator.validateSeatInTheater(1, 2, seatRepo));
        assertThrows(InvalidInputException.class, () -> Validator.validateSeatInTheater(999, 1, seatRepo));
    }
}
