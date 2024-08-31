package com.ebay.interview.flexiblecalculator.utils;

import com.ebay.interview.flexiblecalculator.model.Token;
import com.ebay.interview.flexiblecalculator.model.TokenType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Component
public class ExpressionParser {

    public List<Token> parse(String expression) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder numberBuilder = new StringBuilder();
        Stack<Character> parenthesesStack = new Stack<>();

        for (char ch : expression.toCharArray()) {
            if (Character.isDigit(ch) || ch == '.') {
                numberBuilder.append(ch);
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                if (numberBuilder.length() > 0) {
                    tokens.add(new Token(TokenType.NUMBER, numberBuilder.toString()));
                    numberBuilder.setLength(0);
                }
                tokens.add(new Token(TokenType.OPERATOR, String.valueOf(ch)));
            } else if (ch == '(') {
                if (numberBuilder.length() > 0) {
                    tokens.add(new Token(TokenType.NUMBER, numberBuilder.toString()));
                    numberBuilder.setLength(0);
                }
                tokens.add(new Token(TokenType.PARENTHESIS, "("));
                parenthesesStack.push(ch);
            } else if (ch == ')') {
                if (numberBuilder.length() > 0) {
                    tokens.add(new Token(TokenType.NUMBER, numberBuilder.toString()));
                    numberBuilder.setLength(0);
                }
                tokens.add(new Token(TokenType.PARENTHESIS, ")"));
                if (parenthesesStack.isEmpty()) {
                    throw new IllegalArgumentException("Mismatched parentheses: Extra closing parenthesis");
                }
                parenthesesStack.pop();
            } else if (Character.isWhitespace(ch)) {
                // Ignore whitespace
                continue;
            } else {
                throw new IllegalArgumentException("Invalid character in expression: ");
            }
        }

        if (numberBuilder.length() > 0) {
            tokens.add(new Token(TokenType.NUMBER, numberBuilder.toString()));
        }

        if (!parenthesesStack.isEmpty()) {
            throw new IllegalArgumentException("Mismatched parentheses: Extra opening parenthesis");
        }

        return tokens;
    }
}