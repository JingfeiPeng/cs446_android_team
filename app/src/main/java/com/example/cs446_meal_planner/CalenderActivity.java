package com.example.cs446_meal_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;
import java.util.Calendar;

public class CalenderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        this.updateDates(0);
    }

    public void updateDates(int week) {
        TextView[] dates = {
                findViewById(R.id.calender_date1),
                findViewById(R.id.calender_date2),
                findViewById(R.id.calender_date3),
                findViewById(R.id.calender_date4),
                findViewById(R.id.calender_date5),
                findViewById(R.id.calender_date6),
                findViewById(R.id.calender_date7),
        };
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);

        for (int i=0; i < dates.length; i++){
            c.add(Calendar.DATE, 1+7*week);
            String[] formatter = c.getTime().toString().split(" ");
            String get_date = "";
            for (int j = 0; j < 3; j++){
                get_date += formatter[j] + " ";
            }
            dates[i].setText(get_date);
        }
    }
}