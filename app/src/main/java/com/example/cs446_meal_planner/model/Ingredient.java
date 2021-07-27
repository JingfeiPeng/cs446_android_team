package com.example.cs446_meal_planner.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Ingredient {
    private String name;
    private Integer calorie;
    private Integer amount;
    private Unit unit;

    public Ingredient(String name, Integer amount, Unit unit, Integer calorie) {
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

    public Integer getCalorie() {
        return calorie;
    }

    public void setCalorie(Integer calorie) {
        this.calorie = calorie;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}