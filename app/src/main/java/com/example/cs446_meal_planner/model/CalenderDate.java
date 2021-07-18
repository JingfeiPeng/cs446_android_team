package com.example.cs446_meal_planner.model;


import org.joda.time.DateTime;

public class CalenderDate {
    public DateTime date;

    public CalenderDate(DateTime date){
        this.date = date;
    }

    public Integer getIntger(){
        return Integer.valueOf(date.getYear()*10000+date.getMonthOfYear()*100+date.getDayOfMonth());
    }

    public String getString() {
        return String.valueOf(date.getYear()) + "-" + String.valueOf(date.getMonthOfYear()) + "-" + String.valueOf(date.getDayOfMonth());
    }
}
