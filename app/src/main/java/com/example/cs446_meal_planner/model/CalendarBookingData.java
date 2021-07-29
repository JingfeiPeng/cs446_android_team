package com.example.cs446_meal_planner.model;

public class CalendarBookingData {
    public CalendarDate date;
    public String mealType;

    public CalendarBookingData(CalendarDate d, String m) {
        this.date = d;
        this.mealType = m;
    }
}