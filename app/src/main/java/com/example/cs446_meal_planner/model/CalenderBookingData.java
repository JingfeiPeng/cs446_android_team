package com.example.cs446_meal_planner.model;

public class CalenderBookingData {
    public CalenderDate date;
    public String mealType;

    public CalenderBookingData(CalenderDate d, String m) {
        this.date = d;
        this.mealType = m;
    }
}