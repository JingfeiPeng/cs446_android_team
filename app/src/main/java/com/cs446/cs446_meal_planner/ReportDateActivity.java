package com.cs446.cs446_meal_planner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import org.joda.time.DateTime;

public class ReportDateActivity extends AppCompatActivity {
    DatePicker picker;
    Button buttonGetDate;
    boolean setStartDate;
    boolean setEndDate;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_calendar);
        setStartDate = getIntent().getExtras().getString("startDate") != null ? true : false;
        setEndDate = getIntent().getExtras().getString("endDate") != null ? true : false;

        picker = findViewById(R.id.date_picker_report);
        buttonGetDate = findViewById(R.id.button_pick_date);

        buttonGetDate.setOnClickListener(v -> {
            getDate();
        });
    }

    private void getDate() {
        DateTime setDate =  new DateTime(picker.getYear(), picker.getMonth()+1, picker.getDayOfMonth(), 0, 0, 0);;
        if (setStartDate) {
            MainActivity.reportStartDate = setDate;
        }
        if (setEndDate) {
            MainActivity.reportEndDate = setDate;
        }
        finish();
    }
}
