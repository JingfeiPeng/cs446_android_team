package com.example.cs446_meal_planner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import org.joda.time.DateTime;

public class ReportDateActivity extends AppCompatActivity {
    DatePicker picker;
    Button buttonGetDate;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_calendar);
        picker = findViewById(R.id.date_picker_report);
        buttonGetDate = findViewById(R.id.button_pick_date);

        buttonGetDate.setOnClickListener(v -> {
            getDate();
        });
    }

    private void getDate() {
        MainActivity.reportStartDate = new DateTime(picker.getYear(), picker.getMonth()+1, picker.getDayOfMonth(), 0, 0, 0);
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.putExtra("date1", picker.getMonth()+1 + "/" + picker.getDayOfMonth());
//        startActivity(intent);
        finish();
    }
}
