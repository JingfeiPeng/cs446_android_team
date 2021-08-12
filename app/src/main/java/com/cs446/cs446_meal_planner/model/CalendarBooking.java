package com.cs446.cs446_meal_planner.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalendarBooking {
    private String mealType;
    private Integer recipeId;
    private Integer mealDate;
    private Recipe bookedRecipe;
}
