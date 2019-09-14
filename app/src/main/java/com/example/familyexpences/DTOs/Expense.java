package com.example.familyexpences.DTOs;

import java.math.BigDecimal;

public class Expense {

    private String userName;
    private String description;
    private BigDecimal price;
    private String category;
    private long dateOfAdding;

    public Expense(String userName, String description, BigDecimal price, String category, long dateOfAdding) {
        this.userName = userName;
        this.description = description;
        this.price = price;
        this.category = category;
        this.dateOfAdding = dateOfAdding;
    }

    public String getUserName() {
        return userName;
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

    public long getDateOfAdding() {
        return dateOfAdding;
    }
}