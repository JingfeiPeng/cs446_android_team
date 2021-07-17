package com.example.cs446_meal_planner.model;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalenderBooking {
    private String meal_type;
    private Integer recipe_id;
    private Date meal_date;

    public Date getMeal_date() {
        return meal_date;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public Integer getRecipe_id() {
        return recipe_id;
    }

    public void setMeal_date(Date meal_date) {
        this.meal_date = meal_date;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }

    public void setRecipe_id(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }
}
