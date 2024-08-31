package com.ebay.interview.flexblecalculator.service.test;

import com.ebay.interview.flexiblecalculator.model.Token;
import com.ebay.interview.flexiblecalculator.model.TokenType;
import com.ebay.interview.flexiblecalculator.service.ExpressionEvaluator;
import com.ebay.interview.flexiblecalculator.service.RangeValidator;
import com.ebay.interview.flexiblecalculator.utils.ExpressionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ExpressionEvaluatorTest {
    private ExpressionEvaluator expressionEvaluator;
    private ExpressionParser expressionParser;
    private RangeValidator rangeValidator;

    @BeforeEach
    void setUp() {
        expressionParser = mock(ExpressionParser.class);
        rangeValidator = mock(RangeValidator.class);
        expressionEvaluator = new ExpressionEvaluator(expressionParser, rangeValidator);

//        // Set default input and output ranges
//        when(rangeValidator.validateInput(anyDouble())).thenReturn(null);
//        when(rangeValidator.validateOutput(anyDouble())).thenReturn(null);
    }

    // Normal Case: Simple addition
    @Test
    void testSimpleAddition() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.NUMBER, "3"),
                new Token(TokenType.OPERATOR, "+"),
                new Token(TokenType.NUMBER, "2")
        );

        when(expressionParser.parse("3+2")).thenReturn(tokens);

        double result = expressionEvaluator.evaluate("3+2");

        verify(rangeValidator).validateInput(3.0);
        verify(rangeValidator).validateInput(2.0);
        verify(rangeValidator).validateOutput(5.0);

        assertEquals(5.0, result);
    }

    // Normal Case: Complex expression with multiple operators
    @Test
    void testComplexExpression() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.NUMBER, "3"),
                new Token(TokenType.OPERATOR, "+"),
                new Token(TokenType.NUMBER, "2"),
                new Token(TokenType.OPERATOR, "*"),
                new Token(TokenType.NUMBER, "4")
        );

        when(expressionParser.parse("3+2*4")).thenReturn(tokens);

        double result = expressionEvaluator.evaluate("3+2*4");

        verify(rangeValidator).validateInput(3.0);
        verify(rangeValidator).validateInput(2.0);
        verify(rangeValidator).validateInput(4.0);
        verify(rangeValidator).validateOutput(11.0);

        assertEquals(11.0, result);
    }

    // Normal Case: Parentheses to enforce precedence
    @Test
    void testParenthesesExpression() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.PARENTHESIS, "("),
                new Token(TokenType.NUMBER, "3"),
                new Token(TokenType.OPERATOR, "+"),
                new Token(TokenType.NUMBER, "2"),
                new Token(TokenType.PARENTHESIS, ")"),
                new Token(TokenType.OPERATOR, "*"),
                new Token(TokenType.NUMBER, "4")
        );

        when(expressionParser.parse("(3+2)*4")).thenReturn(tokens);

        double result = expressionEvaluator.evaluate("(3+2)*4");

        verify(rangeValidator).validateInput(3.0);
        verify(rangeValidator).validateInput(2.0);
        verify(rangeValidator).validateInput(4.0);
        verify(rangeValidator).validateOutput(20.0);

        assertEquals(20.0, result);
    }

    // Edge Case: Division by zero
    @Test
    void testDivisionByZero() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.NUMBER, "10"),
                new Token(TokenType.OPERATOR, "/"),
                new Token(TokenType.NUMBER, "0")
        );

        when(expressionParser.parse("10/0")).thenReturn(tokens);

        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            expressionEvaluator.evaluate("10/0");
        });

        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    // Edge Case: Empty expression
    @Test
    void testEmptyExpression() {
        List<Token> tokens = Collections.emptyList();

        when(expressionParser.parse("")).thenReturn(tokens);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            expressionEvaluator.evaluate("");
        });

        assertEquals("Expression cannot be empty", exception.getMessage());
    }

    // Edge Case: Invalid operator placement
    @Test
    void testInvalidOperatorPlacement() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.OPERATOR, "+"),
                new Token(TokenType.NUMBER, "2")
        );

        when(expressionParser.parse("+2")).thenReturn(tokens);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            expressionEvaluator.evaluate("+2");
        });

        assertEquals("Invalid operator placement", exception.getMessage());
    }

    // Edge Case: Out of range input
    @Test
    void testOutOfRangeInput() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.NUMBER, "1000001"),
                new Token(TokenType.OPERATOR, "+"),
                new Token(TokenType.NUMBER, "1")
        );

        when(expressionParser.parse("1000001+1")).thenReturn(tokens);
        doThrow(new IllegalArgumentException("Input value out of range: 1000001.0"))
                .when(rangeValidator).validateInput(1000001.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            expressionEvaluator.evaluate("1000001+1");
        });

        assertEquals("Input value out of range: 1000001.0", exception.getMessage());
    }

    // Edge Case: Out of range output
    @Test
    void testOutOfRangeOutput() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.NUMBER, "1000000"),
                new Token(TokenType.OPERATOR, "+"),
                new Token(TokenType.NUMBER, "1")
        );

        when(expressionParser.parse("1000000+1")).thenReturn(tokens);
        doThrow(new IllegalArgumentException("Output value out of range: 1000001.0"))
                .when(rangeValidator).validateOutput(1000001.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            expressionEvaluator.evaluate("1000000+1");
        });

        assertEquals("Output value out of range: 1000001.0", exception.getMessage());
    }

    // Edge Case: Nested parentheses
    @Test
    void testNestedParentheses() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.PARENTHESIS, "("),
                new Token(TokenType.NUMBER, "3"),
                new Token(TokenType.OPERATOR, "+"),
                new Token(TokenType.PARENTHESIS, "("),
                new Token(TokenType.NUMBER, "2"),
                new Token(TokenType.OPERATOR, "*"),
                new Token(TokenType.NUMBER, "2"),
                new Token(TokenType.PARENTHESIS, ")"),
                new Token(TokenType.PARENTHESIS, ")"),
                new Token(TokenType.OPERATOR, "*"),
                new Token(TokenType.NUMBER, "2")
        );

        when(expressionParser.parse("(3+(2*2))*2")).thenReturn(tokens);

        double result = expressionEvaluator.evaluate("(3+(2*2))*2");

        verify(rangeValidator).validateInput(3.0);
        verify(rangeValidator).validateOutput(14.0);

        assertEquals(14.0, result);
    }

    // Edge Case: Single number without operator
    @Test
    void testSingleNumber() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.NUMBER, "5")
        );

        when(expressionParser.parse("5")).thenReturn(tokens);
        double result = expressionEvaluator.evaluate("5");
        verify(rangeValidator).validateInput(5.0);
        assertEquals(5.0, result);
    }
}