package com.example.cs446_meal_planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.cs446_meal_planner.model.CalendarAdapter;
import com.example.cs446_meal_planner.model.CalendarDate;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    private int week = 0;
    private int daysToShow = 7;
    RecyclerView recyclerCalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        this.updateDates();
    }

    public void increaseWeek(View view) {
        this.week += 1;
        updateDates();
    }

    public void decreaseWeek(View view) {
        this.week -= 1;
        updateDates();
    }

    public void updateDates() {
        DateTimeFormatter parser = ISODateTimeFormat.date();
        Date dt = new Date();

        DateTime datePtr = new DateTime(dt);
        datePtr = datePtr.plusDays(daysToShow*this.week);
        ArrayList<CalendarDate> dates = new ArrayList<>();
        dates.add(new CalendarDate(datePtr));
        for (int i=1; i<daysToShow;i++){
            dates.add(new CalendarDate(datePtr.plusDays(i+daysToShow*this.week)));
        }

        recyclerCalender = findViewById(R.id.recycler_calender);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerCalender.setLayoutManager(layoutManager);
        recyclerCalender.setAdapter(new CalendarAdapter(this,dates));
    }
}