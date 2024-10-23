package com.example.codingexercise.gateway.dto;

import java.util.Map;

public record ExchangeRates(Double amount, String base, String date, Map<String, Double> rates) {
    
}
