package com.cinema.util;

import com.cinema.exception.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

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
}
