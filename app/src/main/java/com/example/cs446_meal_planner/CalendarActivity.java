package com.example.cs446_meal_planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cs446_meal_planner.model.CalendarAdapter;
import com.example.cs446_meal_planner.model.CalendarDate;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;
import java.util.Date;

public class CalendarActivity extends ObserverActivity {

    private int week;
    private int daysToShow = 7;
    RecyclerView recyclerCalender;
    CalendarDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        week = 0;
        setContentView(R.layout.activity_calendar);
        db = CalendarDBHelper.getInstance(this);
        db.attachActivity(this);
        this.update();
    }

    @Override
    public void onDestroy() {
        db.detchActivity(this);
        super.onDestroy();
    }

    public void increaseWeek(View view) {
        this.week += 1;
        update();
    }

    public void decreaseWeek(View view) {
        this.week -= 1;
        update();
    }

    @Override
    public void update() {
        DateTimeFormatter parser = ISODateTimeFormat.date();
        Date dt = new Date();

        DateTime datePtr = new DateTime(dt);
        ArrayList<CalendarDate> dates = new ArrayList<>();
        dates.add(new CalendarDate(datePtr.plusDays(daysToShow*this.week)));
        for (int i=1; i<daysToShow;i++){
            dates.add(new CalendarDate(datePtr.plusDays(i+daysToShow*this.week)));
        }

        recyclerCalender = findViewById(R.id.recycler_calender);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerCalender.setLayoutManager(layoutManager);
        recyclerCalender.setAdapter(new CalendarAdapter(this,dates));
    }
}