package com.example.familyexpences.DTOs;

import java.math.BigDecimal;

public class Expense {

    private String description;
    private BigDecimal price;
    private String category;

    public Expense(String description, BigDecimal price, String category) {
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}