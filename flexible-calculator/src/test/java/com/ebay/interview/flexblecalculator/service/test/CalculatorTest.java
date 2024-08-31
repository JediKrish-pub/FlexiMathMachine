package com.ebay.interview.flexblecalculator.service.test;

import com.ebay.interview.flexiblecalculator.service.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    // Normal Case: Addition
    @Test
    void testAddition() {
        double result = calculator.calculate(Operation.ADD, 5.0, 3.0);
        assertEquals(8.0, result);
    }

    // Normal Case: Subtraction
    @Test
    void testSubtraction() {
        double result = calculator.calculate(Operation.SUBTRACT, 5.0, 3.0);
        assertEquals(2.0, result);
    }

    // Normal Case: Multiplication
    @Test
    void testMultiplication() {
        double result = calculator.calculate(Operation.MULTIPLY, 5.0, 3.0);
        assertEquals(15.0, result);
    }

    // Normal Case: Division
    @Test
    void testDivision() {
        double result = calculator.calculate(Operation.DIVIDE, 6.0, 3.0);
        assertEquals(2.0, result);
    }

    // Edge Case: Division by zero
    @Test
    void testDivisionByZero() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            calculator.calculate(Operation.DIVIDE, 6.0, 0.0);
        });
        assertEquals("Division by zero", exception.getMessage());
    }

    // Edge Case: Unsupported operation
    @Test
    void testUnsupportedOperation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(null, 6.0, 3.0);
        });
        assertEquals("Operation not supported: null", exception.getMessage());
    }

    // Edge Case: Large numbers multiplication
    @Test
    void testLargeNumbersMultiplication() {
        double result = calculator.calculate(Operation.MULTIPLY, 1e308, 1e-308);
        assertEquals(1.0, result, 1e-10);
    }

    // Edge Case: Negative numbers addition
    @Test
    void testNegativeNumbersAddition() {
        double result = calculator.calculate(Operation.ADD, -5.0, -3.0);
        assertEquals(-8.0, result);
    }

    // Edge Case: Subtraction resulting in zero
    @Test
    void testSubtractionResultingInZero() {
        double result = calculator.calculate(Operation.SUBTRACT, 5.0, 5.0);
        assertEquals(0.0, result);
    }

    // Edge Case: Division resulting in a small number
    @Test
    void testDivisionResultingInSmallNumber() {
        double result = calculator.calculate(Operation.DIVIDE, 1.0, 1e10);
        assertEquals(1e-10, result, 1e-15);
    }

    // Edge Case: Overflow in addition
    @Test
    void testOverflowInAddition() {
        double result = calculator.calculate(Operation.ADD, Double.MAX_VALUE, Double.MAX_VALUE);
        assertEquals(Double.POSITIVE_INFINITY, result);
    }
}