package com.ebay.interview.flexiblecalculator.service;

import org.springframework.expression.Operation;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Service
public class Calculator {

    private final Map<Operation, BiFunction<Double, Double, Double>> operationMap = new HashMap<>();

    public Calculator() {
        // Initialize the operation map with supported operations
        operationMap.put(Operation.ADD, Double::sum);
        operationMap.put(Operation.SUBTRACT, (a, b) -> a - b);
        operationMap.put(Operation.MULTIPLY, (a, b) -> a * b);
        operationMap.put(Operation.DIVIDE, (a, b) -> {
            if (b == 0) {
                throw new ArithmeticException("Division by zero");
            }
            return a / b;
        });
    }

    public double calculate(Operation operation, double num1, double num2) {
        BiFunction<Double, Double, Double> operationFunction = operationMap.get(operation);
        if (operationFunction == null) {
            throw new IllegalArgumentException("Operation not supported: " + operation);
        }
        return operationFunction.apply(num1, num2);
    }
}