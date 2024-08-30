package com.ebay.interview.flexiblecalculator.component;

import com.ebay.interview.flexiblecalculator.service.UserInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CalculatorCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserInteractionService userInteractionService;

    @Override
    public void run(String... args) {
        userInteractionService.start();
    }
}