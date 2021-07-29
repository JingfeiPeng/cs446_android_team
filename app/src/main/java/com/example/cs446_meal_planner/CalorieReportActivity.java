package com.example.cs446_meal_planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs446_meal_planner.model.MonthDay;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.joda.time.DateTime;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class CalorieReportActivity extends AppCompatActivity {

    GraphView calorieGraph;

//    private Map<MonthDay, Double> breakfastList;
//    private Map<MonthDay, Double> lunchList;
//    private Map<MonthDay, Double> dinnerList;
//    private Map<MonthDay, Double> totalList;
    private DateTime startDate;
    private int days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_calorie);
        calorieGraph = findViewById(R.id.graph_calorie);
        Intent intent = getIntent();

//        breakfastList = (LinkedHashMap<MonthDay, Double>) getIntent().getSerializableExtra("breakfastList");
//        lunchList = (LinkedHashMap<MonthDay, Double>) getIntent().getSerializableExtra("lunchList");
//        dinnerList = (LinkedHashMap<MonthDay, Double>) getIntent().getSerializableExtra("dinnerList");
//        dinnerList = (LinkedHashMap<MonthDay, Double>) getIntent().getSerializableExtra("totalList");
        startDate = (DateTime) getIntent().getSerializableExtra("startDate");
        days = (int)getIntent().getSerializableExtra("index");

        List<String> xLabels = new ArrayList<>();
        for(int i = 0; i < days; i++){
            MonthDay temp = MonthDay.builder()
                    .month(startDate.plusDays(i).getMonthOfYear())
                    .day(startDate.plusDays(i).getDayOfMonth())
                    .build();

            xLabels.add(getMonth(temp.getMonth()) + temp.getDay());
        }

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(calorieGraph);
        String [] temp = new String[xLabels.size()];

        for(int i = 0; i < xLabels.size(); i++){
            temp[i] = xLabels.get(i);
        }

        staticLabelsFormatter.setHorizontalLabels(temp);
        calorieGraph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        // set manual X bounds
        calorieGraph.getViewport().setYAxisBoundsManual(true);
        calorieGraph.getViewport().setMinY(-150);
        calorieGraph.getViewport().setMaxY(150);

        calorieGraph.getViewport().setXAxisBoundsManual(true);
        calorieGraph.getViewport().setMinX(4);
        calorieGraph.getViewport().setMaxX(80);

        // enable scaling and scrolling
        calorieGraph.getViewport().setScalable(true);
        calorieGraph.getViewport().setScalableY(true);
    }

    public void viewBreakfastReport(View v)
    {
        LineGraphSeries<DataPoint> series = generateSeries(ReportActivity.breakfastList);
        calorieGraph.removeAllSeries();
        calorieGraph.addSeries(series);
    }

    public void viewLunchReport(View v)
    {
        LineGraphSeries<DataPoint> series = generateSeries(ReportActivity.lunchList);
        calorieGraph.removeAllSeries();
        calorieGraph.addSeries(series);
    }

    public void viewDinnerReport(View v)
    {
        LineGraphSeries<DataPoint> series = generateSeries(ReportActivity.dinnerList);
        calorieGraph.removeAllSeries();
        calorieGraph.addSeries(series);
    }

    public void viewTotalReport(View v)
    {
        LineGraphSeries<DataPoint> series = generateSeries(ReportActivity.totalList);
        calorieGraph.removeAllSeries();
        calorieGraph.addSeries(series);
    }

    private LineGraphSeries<DataPoint> generateSeries(Map<MonthDay, Double> list){
        List<DataPoint> datapoints = new ArrayList<>();

        for(int i = 0; i < days; i++){
            MonthDay temp = MonthDay.builder()
                    .month(startDate.plusDays(i).getMonthOfYear())
                    .day(startDate.plusDays(i).getDayOfMonth())
                    .build();

            Double calorie = 0.0;

            if(list.containsKey(temp)){
                calorie = list.get(temp);
            }

            datapoints.add(new DataPoint(i, calorie));
        }

        DataPoint [] temp = new DataPoint[datapoints.size()];

        for(int i = 0; i < datapoints.size(); i++){
            temp[i] = datapoints.get(i);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(temp);
        return series;
    }

    public String getMonth(int month) {
        return new DateFormatSymbols(new Locale("en")).getMonths()[month-1];
    }
}
