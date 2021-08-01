package com.example.cs446_meal_planner.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonalInfo {
    private String gender;
    private Integer age;
    private Integer goal;
}
