package com.ebay.interview.flexblecalculator.service.test;

import com.ebay.interview.flexiblecalculator.service.RangeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RangeValidatorTest {
    private RangeValidator rangeValidator;

    @BeforeEach
    void setUp() {
        rangeValidator = new RangeValidator();
    }

    // Test setting input range
    @Test
    void testSetInputRange() {
        rangeValidator.setInputRange(-1000, 1000);
        rangeValidator.validateInput(0); // Within range
    }

    @Test
    void testSetInputRangeWithNegativeValues() {
        rangeValidator.setInputRange(-500, 500);
        rangeValidator.validateInput(-250); // Within range
    }

    @Test
    void testSetInputRangeWithPositiveValues() {
        rangeValidator.setInputRange(0, 1000);
        rangeValidator.validateInput(500); // Within range
    }

    // Test input range validation
    @Test
    void testValidateInputWithinRange() {
        rangeValidator.setInputRange(-1000, 1000);
        assertDoesNotThrow(() -> rangeValidator.validateInput(500)); // Valid input
    }

    @Test
    void testValidateInputAtLowerBoundary() {
        rangeValidator.setInputRange(-1000, 1000);
        assertDoesNotThrow(() -> rangeValidator.validateInput(-1000)); // Valid input
    }

    @Test
    void testValidateInputAtUpperBoundary() {
        rangeValidator.setInputRange(-1000, 1000);
        assertDoesNotThrow(() -> rangeValidator.validateInput(1000)); // Valid input
    }

    @Test
    void testValidateInputBelowRange() {
        rangeValidator.setInputRange(-1000, 1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateInput(-1001); // Invalid input
        });
        assertEquals("Input value out of range: -1001.0. Valid range: -1000.0 to 1000.0", exception.getMessage());
    }

    @Test
    void testValidateInputAboveRange() {
        rangeValidator.setInputRange(-1000, 1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateInput(1001); // Invalid input
        });
        assertEquals("Input value out of range: 1001.0. Valid range: -1000.0 to 1000.0", exception.getMessage());
    }

    // Test setting output range
    @Test
    void testSetOutputRange() {
        rangeValidator.setOutputRange(-1000, 1000);
        rangeValidator.validateOutput(0); // Within range
    }

    @Test
    void testSetOutputRangeWithNegativeValues() {
        rangeValidator.setOutputRange(-500, 500);
        rangeValidator.validateOutput(-250); // Within range
    }

    @Test
    void testSetOutputRangeWithPositiveValues() {
        rangeValidator.setOutputRange(0, 1000);
        rangeValidator.validateOutput(500); // Within range
    }

    // Test output range validation
    @Test
    void testValidateOutputWithinRange() {
        rangeValidator.setOutputRange(-1000, 1000);
        assertDoesNotThrow(() -> rangeValidator.validateOutput(500)); // Valid output
    }

    @Test
    void testValidateOutputAtLowerBoundary() {
        rangeValidator.setOutputRange(-1000, 1000);
        assertDoesNotThrow(() -> rangeValidator.validateOutput(-1000)); // Valid output
    }

    @Test
    void testValidateOutputAtUpperBoundary() {
        rangeValidator.setOutputRange(-1000, 1000);
        assertDoesNotThrow(() -> rangeValidator.validateOutput(1000)); // Valid output
    }

    @Test
    void testValidateOutputBelowRange() {
        rangeValidator.setOutputRange(-1000, 1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateOutput(-1001); // Invalid output
        });
        assertEquals("Output value out of range: -1001.0. Valid range: -1000.0 to 1000.0", exception.getMessage());
    }

    @Test
    void testValidateOutputAboveRange() {
        rangeValidator.setOutputRange(-1000, 1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateOutput(1001); // Invalid output
        });
        assertEquals("Output value out of range: 1001.0. Valid range: -1000.0 to 1000.0", exception.getMessage());
    }

    // Test with large values for stress testing
    @Test
    void testLargeInputRange() {
        rangeValidator.setInputRange(-1e6, 1e6);
        assertDoesNotThrow(() -> rangeValidator.validateInput(999999)); // Valid input
    }

    @Test
    void testLargeOutputRange() {
        rangeValidator.setOutputRange(-1e6, 1e6);
        assertDoesNotThrow(() -> rangeValidator.validateOutput(-999999)); // Valid output
    }

    @Test
    void testInputAtMaximumDoubleValue() {
        rangeValidator.setInputRange(Double.MIN_VALUE, Double.MAX_VALUE);
        assertDoesNotThrow(() -> rangeValidator.validateInput(Double.MAX_VALUE)); // Valid input
    }

    @Test
    void testOutputAtMaximumDoubleValue() {
        rangeValidator.setOutputRange(Double.MIN_VALUE, Double.MAX_VALUE);
        assertDoesNotThrow(() -> rangeValidator.validateOutput(Double.MAX_VALUE)); // Valid output
    }

    @Test
    void testInputAtMinimumDoubleValue() {
        rangeValidator.setInputRange(-Double.MAX_VALUE, Double.MAX_VALUE);
        assertDoesNotThrow(() -> rangeValidator.validateInput(-Double.MAX_VALUE)); // Valid input
    }

    @Test
    void testOutputAtMinimumDoubleValue() {
        rangeValidator.setOutputRange(-Double.MAX_VALUE, Double.MAX_VALUE);
        assertDoesNotThrow(() -> rangeValidator.validateOutput(-Double.MAX_VALUE)); // Valid output
    }


    // Edge Case: Input range where min and max are the same
    @Test
    void testInputRangeMinEqualsMax() {
        rangeValidator.setInputRange(100, 100);
        assertDoesNotThrow(() -> rangeValidator.validateInput(100)); // Valid input (exact match)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateInput(101); // Invalid input (out of range)
        });
        assertEquals("Input value out of range: 101.0. Valid range: 100.0 to 100.0", exception.getMessage());
    }

    // Edge Case: Output range where min and max are the same
    @Test
    void testOutputRangeMinEqualsMax() {
        rangeValidator.setOutputRange(-500, -500);
        assertDoesNotThrow(() -> rangeValidator.validateOutput(-500)); // Valid output (exact match)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateOutput(-499); // Invalid output (out of range)
        });
        assertEquals("Output value out of range: -499.0. Valid range: -500.0 to -500.0", exception.getMessage());
    }

    // Edge Case: Input range with extremely close min and max values
    @Test
    void testInputRangeExtremelyCloseMinAndMax() {
        rangeValidator.setInputRange(1.0000001, 1.0000002);
        assertDoesNotThrow(() -> rangeValidator.validateInput(1.00000015)); // Valid input
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateInput(1.0000003); // Invalid input
        });
        assertEquals("Input value out of range: 1.0000003. Valid range: 1.0000001 to 1.0000002", exception.getMessage());
    }

    // Edge Case: Very large negative and positive values
    @Test
    void testExtremeNegativeInput() {
        rangeValidator.setInputRange(-1e12, 1e12);
        assertDoesNotThrow(() -> rangeValidator.validateInput(-1e12)); // Valid input
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateInput(-1.1e12); // Invalid input
        });
        assertEquals("Input value out of range: -1.1E12. Valid range: -1.0E12 to 1.0E12", exception.getMessage());
    }

    @Test
    void testExtremePositiveInput() {
        rangeValidator.setInputRange(-1e12, 1e12);
        assertDoesNotThrow(() -> rangeValidator.validateInput(1e12)); // Valid input
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateInput(1.1e12); // Invalid input
        });
        assertEquals("Input value out of range: 1.1E12. Valid range: -1.0E12 to 1.0E12", exception.getMessage());
    }

    @Test
    void testExtremeNegativeOutput() {
        rangeValidator.setOutputRange(-1e12, 1e12);
        assertDoesNotThrow(() -> rangeValidator.validateOutput(-1e12)); // Valid output
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateOutput(-1.1e12); // Invalid output
        });
        assertEquals("Output value out of range: -1.1E12. Valid range: -1.0E12 to 1.0E12", exception.getMessage());
    }

    @Test
    void testExtremePositiveOutput() {
        rangeValidator.setOutputRange(-1e12, 1e12);
        assertDoesNotThrow(() -> rangeValidator.validateOutput(1e12)); // Valid output
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateOutput(1.1e12); // Invalid output
        });
        assertEquals("Output value out of range: 1.1E12. Valid range: -1.0E12 to 1.0E12", exception.getMessage());
    }

    // Edge Case: Values close to zero
    @Test
    void testInputNearZero() {
        rangeValidator.setInputRange(-0.000001, 0.000001);
        assertDoesNotThrow(() -> rangeValidator.validateInput(0.0000005)); // Valid input
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateInput(0.000002); // Invalid input
        });
        assertEquals("Input value out of range: 2.0E-6. Valid range: -1.0E-6 to 1.0E-6", exception.getMessage());
    }

    @Test
    void testOutputNearZero() {
        rangeValidator.setOutputRange(-0.000001, 0.000001);
        assertDoesNotThrow(() -> rangeValidator.validateOutput(-0.0000005)); // Valid output
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateOutput(-0.000002); // Invalid output
        });
        assertEquals("Output value out of range: -2.0E-6. Valid range: -1.0E-6 to 1.0E-6", exception.getMessage());
    }

    // Edge Case: Setting range to very large numbers
    @Test
    void testVeryLargeRangeInput() {
        rangeValidator.setInputRange(-Double.MAX_VALUE, Double.MAX_VALUE);
        assertDoesNotThrow(() -> rangeValidator.validateInput(Double.MAX_VALUE - 1)); // Valid input
    }

    @Test
    void testVeryLargeRangeOutput() {
        rangeValidator.setOutputRange(-Double.MAX_VALUE, Double.MAX_VALUE);
        assertDoesNotThrow(() -> rangeValidator.validateOutput(Double.MAX_VALUE - 1)); // Valid output
    }

    // Edge Case: Input and output exactly at the range boundaries
    @Test
    void testInputAtBoundary() {
        rangeValidator.setInputRange(-100, 100);
        assertDoesNotThrow(() -> rangeValidator.validateInput(100)); // Valid input (upper boundary)
        assertDoesNotThrow(() -> rangeValidator.validateInput(-100)); // Valid input (lower boundary)
    }

    @Test
    void testOutputAtBoundary() {
        rangeValidator.setOutputRange(-100, 100);
        assertDoesNotThrow(() -> rangeValidator.validateOutput(100)); // Valid output (upper boundary)
        assertDoesNotThrow(() -> rangeValidator.validateOutput(-100)); // Valid output (lower boundary)
    }


    // Edge Case: Input or output as Infinity
    @Test
    void testInputAsInfinity() {
        rangeValidator.setInputRange(-100, 100);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateInput(Double.POSITIVE_INFINITY); // Invalid input
        });
        assertEquals("Input value out of range: Infinity. Valid range: -100.0 to 100.0", exception.getMessage());
    }

    @Test
    void testOutputAsInfinity() {
        rangeValidator.setOutputRange(-100, 100);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rangeValidator.validateOutput(Double.NEGATIVE_INFINITY); // Invalid output
        });
        assertEquals("Output value out of range: -Infinity. Valid range: -100.0 to 100.0", exception.getMessage());
    }
}