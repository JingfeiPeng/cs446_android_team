package com.example.cs446_meal_planner.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Recipe {
    private Integer id;
    private String name;
    private String imageUrl;
    private String ingredients;
    private String instruction;
    private Double cookingTime;
    private Double calorie;
    private String feedbacks;
}
