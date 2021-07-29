package com.example.cs446_meal_planner.model;


import android.util.Log;

import org.joda.time.DateTime;

public class CalendarDate {
    public DateTime date;

    public CalendarDate(DateTime date){
        this.date = date;
    }

    public CalendarDate(int dateInt) {
        int year = dateInt / 10000;
        int month  = (dateInt % 10000) / 100;
        int day = dateInt % 100;
        this.date = new DateTime();
        this.date = this.date.withYear(year);
        this.date = this.date.withMonthOfYear(month);
        this.date = this.date.withDayOfMonth(day);
    }

    public Integer getIntger(){
        return Integer.valueOf(date.getYear()*10000+date.getMonthOfYear()*100+date.getDayOfMonth());
    }

    public String getString() {
        return String.format("%04d-%02d-%02d",date.getYear(),date.getMonthOfYear(), date.getDayOfMonth());
    }
}
