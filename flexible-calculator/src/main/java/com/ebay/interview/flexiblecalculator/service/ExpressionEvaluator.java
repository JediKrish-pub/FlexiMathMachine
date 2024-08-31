package com.ebay.interview.flexiblecalculator.service;

import com.ebay.interview.flexiblecalculator.model.Token;
import com.ebay.interview.flexiblecalculator.model.TokenType;
import com.ebay.interview.flexiblecalculator.utils.ExpressionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;
@Service
public class ExpressionEvaluator {

    private final ExpressionParser expressionParser;
    private final RangeValidator rangeValidator;

    @Autowired
    public ExpressionEvaluator(ExpressionParser expressionParser, RangeValidator rangeValidator) {
        this.expressionParser = expressionParser;
        this.rangeValidator = rangeValidator;

        // Example range setup: Adjust these values as per your requirements
        this.rangeValidator.setInputRange(-1e6, 1e6); // Setting input range from -1,000,000 to 1,000,000
        this.rangeValidator.setOutputRange(-1e6, 1e6); // Setting output range from -1,000,000 to 1,000,000
    }

    public double evaluate(String expression) {
        List<Token> tokens = expressionParser.parse(expression);
        validateTokens(tokens); // Validate tokens before evaluation
        return evaluateTokens(tokens);
    }

    private void validateTokens(List<Token> tokens) {
        // Add token validation logic here
        // For example, check for invalid token sequences
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be empty");
        }

        // Check for invalid token sequences
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.getType() == TokenType.OPERATOR) {
                // Check for operator at the beginning or end
                if (i == 0 || i == tokens.size() - 1 || tokens.get(i + 1).getType() == TokenType.OPERATOR) {
                    throw new IllegalArgumentException("Invalid operator placement");
                }
            }
        }
    }

    private double evaluateTokens(List<Token> tokens) {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (Token token : tokens) {
            if (token.getType() == TokenType.NUMBER) {
                double value = Double.parseDouble(token.getValue());

                // Validate input value
                rangeValidator.validateInput(value);

                values.push(value);
            } else if (token.getType() == TokenType.OPERATOR) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token.getValue().charAt(0))) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(token.getValue().charAt(0));
            } else if (token.getType() == TokenType.PARENTHESIS) {
                if (token.getValue().equals("(")) {
                    operators.push('(');
                } else {
                    while (operators.peek() != '(') {
                        values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                    }
                    operators.pop();
                }
            }
        }

        while (!operators.isEmpty()) {
            double result = applyOperator(operators.pop(), values.pop(), values.pop());

            // Validate output value
            rangeValidator.validateOutput(result);

            values.push(result);
        }

        return values.pop();
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private double applyOperator(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Cannot divide by zero");
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}