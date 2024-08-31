package com.ebay.interview.flexiblecalculator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;
@Service
public class UserInteractionService {

    private static final Logger logger = LoggerFactory.getLogger(UserInteractionService.class);

    private final ExpressionEvaluator expressionEvaluator;

    @Autowired
    public UserInteractionService(ExpressionEvaluator expressionEvaluator) {
        this.expressionEvaluator = expressionEvaluator;
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            logger.info("Welcome to the Flexible Calculator!");
            logger.info("Input range: -1,000,000` "  + " to  1,000,000`");
            logger.info("Output range: -1,000,000` "  + " to  1,000,000`");

            while (true) {
                logger.info("Enter an expression to evaluate (or 'exit' to quit): ");
                String input = scanner.nextLine();

                if ("exit".equalsIgnoreCase(input)) {
                    logger.info("Exiting the application.");
                    break;
                }

                try {
                    double result = expressionEvaluator.evaluate(input);
                    logger.info("Result: " + result);
                } catch (IllegalArgumentException e) {
                    logger.error("Input/Output value out of range: {}", e.getMessage());
                    logger.info("Error: " + e.getMessage());
                } catch (Exception e) {
                    logger.error("Error evaluating expression: {}", e.getMessage());
                    logger.info("Error: " + e.getMessage());
                }
            }
        }
    }
}