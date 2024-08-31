package com.ebay.interview.flexblecalculator.component.test.com.ebay.interview.flexiblecalculator.utils;

import com.ebay.interview.flexiblecalculator.model.Token;
import com.ebay.interview.flexiblecalculator.model.TokenType;
import com.ebay.interview.flexiblecalculator.utils.ExpressionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionParserTest {


    private ExpressionParser parser;

    @BeforeEach
    public void setUp() {
        parser = new ExpressionParser();
    }

    @Test
    public void testSimpleAddition() {
        String expression = "3 + 4";
        List<Token> expectedTokens = Arrays.asList(
                new Token(TokenType.NUMBER, "3"),
                new Token(TokenType.OPERATOR, "+"),
                new Token(TokenType.NUMBER, "4")
        );
        List<Token> actualTokens = parser.parse(expression);
        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    public void testMultipleOperators() {
        String expression = "10 - 5 * 3 / 2";
        List<Token> expectedTokens = Arrays.asList(
                new Token(TokenType.NUMBER, "10"),
                new Token(TokenType.OPERATOR, "-"),
                new Token(TokenType.NUMBER, "5"),
                new Token(TokenType.OPERATOR, "*"),
                new Token(TokenType.NUMBER, "3"),
                new Token(TokenType.OPERATOR, "/"),
                new Token(TokenType.NUMBER, "2")
        );
        List<Token> actualTokens = parser.parse(expression);
        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    public void testParentheses() {
        String expression = "2 * (3 + 4)";
        List<Token> expectedTokens = Arrays.asList(
                new Token(TokenType.NUMBER, "2"),
                new Token(TokenType.OPERATOR, "*"),
                new Token(TokenType.PARENTHESIS, "("),
                new Token(TokenType.NUMBER, "3"),
                new Token(TokenType.OPERATOR, "+"),
                new Token(TokenType.NUMBER, "4"),
                new Token(TokenType.PARENTHESIS, ")")
        );
        List<Token> actualTokens = parser.parse(expression);
        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    public void testNestedParentheses() {
        String expression = "(1 + (2 * 3)) / 4";
        List<Token> expectedTokens = Arrays.asList(
                new Token(TokenType.PARENTHESIS, "("),
                new Token(TokenType.NUMBER, "1"),
                new Token(TokenType.OPERATOR, "+"),
                new Token(TokenType.PARENTHESIS, "("),
                new Token(TokenType.NUMBER, "2"),
                new Token(TokenType.OPERATOR, "*"),
                new Token(TokenType.NUMBER, "3"),
                new Token(TokenType.PARENTHESIS, ")"),
                new Token(TokenType.PARENTHESIS, ")"),
                new Token(TokenType.OPERATOR, "/"),
                new Token(TokenType.NUMBER, "4")
        );
        List<Token> actualTokens = parser.parse(expression);
        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    public void testInvalidCharacter() {
        String expression = "5 & 6";
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(expression)
        );
        assertTrue(thrown.getMessage().contains("Invalid character in expression:"));
    }

    @Test
    public void testMismatchedParenthesesExtraOpening() {
        String expression = "((1 + 2";
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(expression)
        );
        assertTrue(thrown.getMessage().contains("Mismatched parentheses: Extra opening parenthesis"));
    }

    @Test
    public void testMismatchedParenthesesExtraClosing() {
        String expression = "1 + 2))";
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(expression)
        );
        assertTrue(thrown.getMessage().contains("Mismatched parentheses: Extra closing parenthesis"));
    }

    @Test
    public void testEmptyExpression() {
        String expression = "";
        List<Token> expectedTokens = Arrays.asList();
        List<Token> actualTokens = parser.parse(expression);
        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    public void testExpressionWithDecimalNumbers() {
        String expression = "3.5 + 2.75";
        List<Token> expectedTokens = Arrays.asList(
                new Token(TokenType.NUMBER, "3.5"),
                new Token(TokenType.OPERATOR, "+"),
                new Token(TokenType.NUMBER, "2.75")
        );
        List<Token> actualTokens = parser.parse(expression);
        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    public void testExpressionWithWhitespace() {
        String expression = "   7    *    8    ";
        List<Token> expectedTokens = Arrays.asList(
                new Token(TokenType.NUMBER, "7"),
                new Token(TokenType.OPERATOR, "*"),
                new Token(TokenType.NUMBER, "8")
        );
        List<Token> actualTokens = parser.parse(expression);
        assertEquals(expectedTokens, actualTokens);
    }
}