package com.example.cs446_meal_planner.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Ingredient {
    private String name;
    private Double calorie;
    private Double amount;
    private Unit unit;

    public Ingredient(String name, Double amount, Unit unit, Double calorie) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCalorie() {
        return calorie;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}