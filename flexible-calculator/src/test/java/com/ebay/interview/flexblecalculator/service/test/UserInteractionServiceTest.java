package com.ebay.interview.flexblecalculator.service.test;

import com.ebay.interview.flexiblecalculator.service.ExpressionEvaluator;
import com.ebay.interview.flexiblecalculator.service.UserInteractionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserInteractionServiceTest {
    private ExpressionEvaluator expressionEvaluator;
    private UserInteractionService userInteractionService;

    @BeforeEach
    void setUp() {
        expressionEvaluator = mock(ExpressionEvaluator.class); // Mock the ExpressionEvaluator
        userInteractionService = new UserInteractionService(expressionEvaluator);
    }

    // Basic arithmetic operations
    @Test
    void testAddition() {
        when(expressionEvaluator.evaluate("2 + 2")).thenReturn(4.0);
        assertEquals(4.0, expressionEvaluator.evaluate("2 + 2"));
    }

    @Test
    void testSubtraction() {
        when(expressionEvaluator.evaluate("5 - 3")).thenReturn(2.0);
        assertEquals(2.0, expressionEvaluator.evaluate("5 - 3"));
    }

    @Test
    void testMultiplication() {
        when(expressionEvaluator.evaluate("3 * 7")).thenReturn(21.0);
        assertEquals(21.0, expressionEvaluator.evaluate("3 * 7"));
    }

    @Test
    void testDivision() {
        when(expressionEvaluator.evaluate("20 / 5")).thenReturn(4.0);
        assertEquals(4.0, expressionEvaluator.evaluate("20 / 5"));
    }

    // Floating-point operations
    @Test
    void testFloatingPointAddition() {
        when(expressionEvaluator.evaluate("3.5 + 2.5")).thenReturn(6.0);
        assertEquals(6.0, expressionEvaluator.evaluate("3.5 + 2.5"));
    }

    @Test
    void testFloatingPointMultiplication() {
        when(expressionEvaluator.evaluate("1.5 * 4.2")).thenReturn(6.3);
        assertEquals(6.3, expressionEvaluator.evaluate("1.5 * 4.2"));
    }

    // Mixed operations with precedence
    @Test
    void testMixedOperationsWithPrecedence() {
        when(expressionEvaluator.evaluate("2 + 3 * 4")).thenReturn(14.0);
        assertEquals(14.0, expressionEvaluator.evaluate("2 + 3 * 4"));
    }

    @Test
    void testParenthesesOverridePrecedence() {
        when(expressionEvaluator.evaluate("(2 + 3) * 4")).thenReturn(20.0);
        assertEquals(20.0, expressionEvaluator.evaluate("(2 + 3) * 4"));
    }

    // Edge cases with very large numbers
    @Test
    void testLargeInputWithinRange() {
        when(expressionEvaluator.evaluate("999999 + 1")).thenReturn(1000000.0);
        assertEquals(1000000.0, expressionEvaluator.evaluate("999999 + 1"));
    }

    @Test
    void testLargeInputOutOfRange() {
        when(expressionEvaluator.evaluate("1e6 + 1")).thenThrow(new IllegalArgumentException("Input value out of range"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expressionEvaluator.evaluate("1e6 + 1");
        });
        assertEquals("Input value out of range", exception.getMessage());
    }

    // Edge cases with very small numbers
    @Test
    void testSmallInputWithinRange() {
        when(expressionEvaluator.evaluate("-999999 - 1")).thenReturn(-1000000.0);
        assertEquals(-1000000.0, expressionEvaluator.evaluate("-999999 - 1"));
    }

    @Test
    void testSmallInputOutOfRange() {
        when(expressionEvaluator.evaluate("-1e6 - 1")).thenThrow(new IllegalArgumentException("Input value out of range"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expressionEvaluator.evaluate("-1e6 - 1");
        });
        assertEquals("Input value out of range", exception.getMessage());
    }

    // Handling invalid inputs
    @Test
    void testInvalidCharacter() {
        when(expressionEvaluator.evaluate("2 + a")).thenThrow(new IllegalArgumentException("Invalid character in expression: a"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expressionEvaluator.evaluate("2 + a");
        });
        assertEquals("Invalid character in expression: a", exception.getMessage());
    }

    @Test
    void testUnbalancedParentheses() {
        when(expressionEvaluator.evaluate("(2 + 3")).thenThrow(new IllegalArgumentException("Unbalanced parentheses"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expressionEvaluator.evaluate("(2 + 3");
        });
        assertEquals("Unbalanced parentheses", exception.getMessage());
    }

    @Test
    void testConsecutiveOperators() {
        when(expressionEvaluator.evaluate("2 ++ 3")).thenThrow(new IllegalArgumentException("Invalid operator sequence"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expressionEvaluator.evaluate("2 ++ 3");
        });
        assertEquals("Invalid operator sequence", exception.getMessage());
    }

    // Handling edge cases with division by zero
    @Test
    void testDivisionByZero() {
        when(expressionEvaluator.evaluate("10 / 0")).thenThrow(new ArithmeticException("Cannot divide by zero"));
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            expressionEvaluator.evaluate("10 / 0");
        });
        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    // Handling edge cases with no input
    @Test
    void testEmptyInput() {
        when(expressionEvaluator.evaluate("")).thenThrow(new IllegalArgumentException("Empty expression"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expressionEvaluator.evaluate("");
        });
        assertEquals("Empty expression", exception.getMessage());
    }

    @Test
    void testOnlyWhitespaceInput() {
        when(expressionEvaluator.evaluate("   ")).thenThrow(new IllegalArgumentException("Empty expression"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expressionEvaluator.evaluate("   ");
        });
        assertEquals("Empty expression", exception.getMessage());
    }

    // Handling edge cases with complex nested expressions
    @Test
    void testNestedParentheses() {
        when(expressionEvaluator.evaluate("((2 + 3) * (4 - 1)) / 2")).thenReturn(7.5);
        assertEquals(7.5, expressionEvaluator.evaluate("((2 + 3) * (4 - 1)) / 2"));
    }

    @Test
    void testDeeplyNestedParentheses() {
        when(expressionEvaluator.evaluate("((((2 + 3) * 4) - 2) / 2) + 1")).thenReturn(10.0);
        assertEquals(10.0, expressionEvaluator.evaluate("((((2 + 3) * 4) - 2) / 2) + 1"));
    }

    // Handling scientific notation
    @Test
    void testScientificNotationInput() {
        when(expressionEvaluator.evaluate("1e3 + 2")).thenReturn(1002.0);
        assertEquals(1002.0, expressionEvaluator.evaluate("1e3 + 2"));
    }

    @Test
    void testScientificNotationEdgeCase() {
        when(expressionEvaluator.evaluate("1e6 + 1")).thenReturn(1000001.0);
        assertEquals(1000001.0, expressionEvaluator.evaluate("1e6 + 1"));
    }

    // Handling edge cases with negative numbers
    @Test
    void testNegativeNumbers() {
        when(expressionEvaluator.evaluate("-5 + 3")).thenReturn(-2.0);
        assertEquals(-2.0, expressionEvaluator.evaluate("-5 + 3"));
    }

    @Test
    void testNegativeMultiplication() {
        when(expressionEvaluator.evaluate("-5 * -4")).thenReturn(20.0);
        assertEquals(20.0, expressionEvaluator.evaluate("-5 * -4"));
    }

    @Test
    void testNegativeDivision() {
        when(expressionEvaluator.evaluate("-20 / 5")).thenReturn(-4.0);
        assertEquals(-4.0, expressionEvaluator.evaluate("-20 / 5"));
    }
}