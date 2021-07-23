package com.example.cs446_meal_planner.model;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalenderBooking {
    private String meal_type;
    private Integer recipe_id;
    private Integer meal_date;
    private Recipe booked_recipe;

    public Recipe getBookedRecipe() {
        return booked_recipe;
    }

    public void setBookedRecipe(Recipe booked_recipe) {
        this.booked_recipe = booked_recipe;
    }


    public Integer getMealDate() {
        return meal_date;
    }

    public String getMealType() {
        return meal_type;
    }

    public Integer getRecipeId() {
        return recipe_id;
    }

    public void setMealDate(Integer meal_date) {
        this.meal_date = meal_date;
    }

    public void setMealType(String meal_type) {
        this.meal_type = meal_type;
    }

    public void setRecipeId(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }
}
