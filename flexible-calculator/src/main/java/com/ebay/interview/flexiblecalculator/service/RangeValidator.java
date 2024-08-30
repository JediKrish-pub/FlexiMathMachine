package com.ebay.interview.flexiblecalculator.service;

import org.springframework.stereotype.Component;

@Component
public class RangeValidator {

    // Default range values
    private double minInput = -Double.MAX_VALUE;  // Minimum acceptable input value
    private double maxInput = Double.MAX_VALUE;   // Maximum acceptable input value
    private double minOutput = -Double.MAX_VALUE; // Minimum acceptable output value
    private double maxOutput = Double.MAX_VALUE;  // Maximum acceptable output value

    // Set the input range
    public void setInputRange(double min, double max) {
        this.minInput = min;
        this.maxInput = max;
        System.out.println("Input range set to: " + min + " to " + max);
    }

    // Set the output range
    public void setOutputRange(double min, double max) {
        this.minOutput = min;
        this.maxOutput = max;
        System.out.println("Output range set to: " + min + " to " + max);
    }

    // Validate input value
    public void validateInput(double value) {
        if (value < minInput || value > maxInput) {
            throw new IllegalArgumentException("Input value out of range: " + value + ". Valid range: " + minInput + " to " + maxInput);
        }
    }

    // Validate output value
    public void validateOutput(double value) {
        if (value < minOutput || value > maxOutput) {
            throw new IllegalArgumentException("Output value out of range: " + value + ". Valid range: " + minOutput + " to " + maxOutput);
        }
    }
}
