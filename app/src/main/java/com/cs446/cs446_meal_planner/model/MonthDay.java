package com.cs446.cs446_meal_planner.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonthDay implements Serializable {
    private int month;
    private int day;
}
