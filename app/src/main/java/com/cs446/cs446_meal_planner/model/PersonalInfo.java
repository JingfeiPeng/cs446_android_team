package com.cs446.cs446_meal_planner.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonalInfo {
    private String gender;
    private Integer age;
    private String activity_level;
    private Integer goal;
}
