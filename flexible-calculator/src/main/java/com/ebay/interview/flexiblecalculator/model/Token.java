package com.ebay.interview.flexiblecalculator.model;

import lombok.Data;

@Data
public class Token {

    private final TokenType type;
    private final String value;
}