package com.example.cs446_meal_planner;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class CalorieReportActivity extends AppCompatActivity {

    protected GraphView calorieGraph;
    private ReportContext reportContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calorieGraph = findViewById(R.id.graph_calorie);

        viewTotalReport(findViewById(android.R.id.content).getRootView());
        initializeGraph();
    }

    public void viewBreakfastReport(View v) { updateGraph(new ReportBreakfast()); }

    public void viewLunchReport(View v) { updateGraph(new ReportLunch()); }

    public void viewDinnerReport(View v) { updateGraph(new ReportDinner()); }

    public void viewTotalReport(View v) { updateGraph(new ReportAll()); }

    private void updateGraph(ReportStrategy strategy) {
        reportContext = new ReportContext(strategy);
        LineGraphSeries series = reportContext.executeStrategy(calorieGraph);
        calorieGraph.removeAllSeries();
        calorieGraph.addSeries(series);
    }

    public String getMonth(int month) {
        return new DateFormatSymbols(new Locale("en")).getMonths()[month-1];
    }

    protected void initializeGraph(){

        // set manual X bounds
        //calorieGraph.getViewport().setYAxisBoundsManual(true);

        double minX = MainActivity.reportStartDate.toDate().getTime();
        double maxX = MainActivity.reportStartDate.plusDays(4).toDate().getTime();
        calorieGraph.getViewport().setXAxisBoundsManual(true);
        calorieGraph.getViewport().setMinX(minX);
        calorieGraph.getViewport().setMaxX(maxX);



        // enable scaling and scrolling
        calorieGraph.getViewport().setScalable(true);
        calorieGraph.getViewport().setScalableY(true);
        calorieGraph.getViewport().setScrollable(true);
        calorieGraph.getViewport().setScrollableY(true);


        DateFormat dateFormat = new SimpleDateFormat("MMMdd");
        calorieGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext(), dateFormat));


        calorieGraph.getGridLabelRenderer().setNumHorizontalLabels(5);
        calorieGraph.getGridLabelRenderer().setHumanRounding(false);

    }

}
